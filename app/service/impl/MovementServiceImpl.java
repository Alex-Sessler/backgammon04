package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import model.dto.MovementDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import service.CheckerService;
import service.DiceService;
import service.GameService;
import service.MovementService;
import service.PlayerService;
import service.PointService;
import service.impl.exception.ForbiddenMoveException;
import backgammon04.model.Board;
import backgammon04.model.Checker;
import backgammon04.model.Dice;
import backgammon04.model.Game;
import backgammon04.model.Movement;
import backgammon04.model.Player;
import backgammon04.model.Point;
import backgammon04.model.User;
import backgammon04.model.dao.MovementDao;
import backgammon04.util.Color;

@Service
public class MovementServiceImpl implements MovementService {

	private static int WHITE_BAR = 25;
	private static int BLACK_BAR = 26;
	private static int WHITE_STACK = 27;
	private static int BLACK_STACK = 28;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MovementDao movementDao;

	@Autowired
	private CheckerService checkerService;

	@Autowired
	private PointService pointService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private DiceService diceService;

	@Autowired
	private GameService gameService;

	@Override
	public MovementDto assembly(Movement movement) {
		MovementDto movementDto = new MovementDto();
		movementDto.setId(movement.getId());
		movementDto.setCreated(movement.getCreated());
		if (movement.getChecker() != null) {
			movementDto.setSourcePoint(Integer.valueOf(movement.getChecker()
					.getPoint().getInternId()));
		}
		if (movement.getDices() != null) {
			movementDto.setDices(diceService.assembly(movement.getDices()));
		}
		if (movement.getTargetPoint() != null) {
			movementDto.setTargetPoint(Integer.valueOf(movement
					.getTargetPoint().getInternId()));
		}

		return movementDto;
	}

	@Override
	public List<MovementDto> assembly(Set<Movement> movementList) {

		List<MovementDto> movementDtoList = new ArrayList<MovementDto>();

		for (Movement movement : movementList) {
			movementDtoList.add(assembly(movement));
		}
		return movementDtoList;

	}

	@Override
	public void move(User user, long gameId, byte sourcePointInternalId,
			byte targetPointInternalId) throws ForbiddenMoveException {
		Game game = gameService.getGame(gameId);
		Player player = playerService.getPlayer(game, user);

		if (game == null) {
			throw new ForbiddenMoveException("Game not found!");
		}

		if (game.getStarted() == null) {
			throw new ForbiddenMoveException("Game not started yet!");
		}

		if (game.getEnded() != null) {
			throw new ForbiddenMoveException("Game is over!");
		}

		if (player == null) {
			throw new ForbiddenMoveException("No player for this game!");
		}

		if (!game.getColor().toString().equals(player.getColor().toString())) {
			throw new ForbiddenMoveException("Not your turn. Please wait!");
		}

		Point sourcePoint = pointService.getPoint(game.getBoard(),
				sourcePointInternalId);
		Point targetPoint = pointService.getPoint(game.getBoard(),
				targetPointInternalId);

		if (sourcePoint == null || targetPoint == null
				|| sourcePoint.getCheckerList() == null) {
			throw new ForbiddenMoveException();
		}

		if (player.getColor() == Color.BLACK) {
			if (sourcePoint.getInternId() != BLACK_BAR
					&& targetPoint.getInternId() > sourcePoint.getInternId()) {
				throw new ForbiddenMoveException();
			}
		} else {
			if (sourcePoint.getInternId() != WHITE_BAR
					&& targetPoint.getInternId() < sourcePoint.getInternId()) {
				throw new ForbiddenMoveException();
			}
		}

		Checker checker = (Checker) sourcePoint.getCheckerList().toArray()[sourcePoint
				.getCheckerList().size() - 1];
		if (checker == null) {
			throw new ForbiddenMoveException();
		}

		byte diceValue = getDiceValue(sourcePoint.getInternId(),
				targetPoint.getInternId());

		if (diceValue > 6 || diceValue < 1) {
			throw new ForbiddenMoveException(
					"Move not in the allowed dice range!");
		}

		if (!checkMovePermission(game, player, checker, targetPoint)) {
			throw new ForbiddenMoveException();
		}

		Movement movement = movementDao.get(game, player);

		if (movement == null) {
			throw new ForbiddenMoveException(
					"Move not in the allowed dice range!");
		}

		Dice dice;
		if ((dice = getDice(movement.getDices(), diceValue)) == null) {
			throw new ForbiddenMoveException("Wrong move range!");
		}

		// Falls ein fremd Checker auf dem target point liegt, move zur bar
		if (targetPoint.getCheckerList().size() == 1) {
			Checker opponentChecker = (Checker) targetPoint.getCheckerList()
					.toArray()[0];
			if (opponentChecker.getPlayer().getColor() != player.getColor()) {
				if (player.getColor() == Color.WHITE) {
					opponentChecker.setPoint(pointService.getPoint(
							game.getBoard(), (byte) BLACK_BAR));
				} else {
					opponentChecker.setPoint(pointService.getPoint(
							game.getBoard(), (byte) WHITE_BAR));
				}
				checkerService.save(opponentChecker);
			}
		}

		// final step, move checker to target
		checker.setPoint(targetPoint);
		checkerService.save(checker);

		movement.setChecker(checker);
		movement.setTargetPoint(targetPoint);
		movementDao.save(movement);
		dice.setPlayed(true);
		diceService.save(dice);

		boolean moveFinished = true;
		for (Dice d : movement.getDices()) {
			if (!d.isPlayed())
				moveFinished = false;
		}

		if (moveFinished) {
			Color nextPlayer = player.getColor() == Color.BLACK ? Color.WHITE
					: Color.BLACK;
			game.setColor(nextPlayer);
		}

		if (pointService.getPoint(game.getBoard(), (byte) WHITE_STACK)
				.getCheckerList().size() == 15) {
			game.setEnded(new Date());
		} else if (pointService.getPoint(game.getBoard(), (byte) BLACK_STACK)
				.getCheckerList().size() == 15) {
			game.setEnded(new Date());
		}

		gameService.save(game);
	}

	private boolean checkMovePermission(Game game, Player player,
			Checker checker, Point targetPoint) {

		// Is the checker, the checker of the current player?
		if (checker.getPlayer().getId() != player.getId()) {
			return false;
		}

		// Wenn auf der white bar ein checker ist, darf von einem anderen point
		// nicht gemoved werden
		if (player.getColor() == Color.WHITE
				&& checker.getPoint().getInternId() != (byte) WHITE_BAR) {
			Point whiteBar = pointService.getPoint(game.getBoard(),
					(byte) WHITE_BAR);
			if (whiteBar.getCheckerList() != null) {
				if (whiteBar.getCheckerList().size() > 0) {
					return false;
				}
			}

		}

		// Wenn auf der black bar ein checker ist, darf von einem anderen point
		// aus nicht gemoved werden
		if (player.getColor() == Color.BLACK
				&& checker.getPoint().getInternId() != (byte) BLACK_BAR) {
			Point blackBar = pointService.getPoint(game.getBoard(),
					(byte) BLACK_BAR);
			if (blackBar.getCheckerList() != null) {
				if (blackBar.getCheckerList().size() > 0) {
					return false;
				}
			}

		}

		// turn possilbe in relation with target pointer?
		if (targetPoint.getCheckerList() != null
				&& targetPoint.getCheckerList().size() > 1) {
			if (((Checker) targetPoint.getCheckerList().toArray()[0])
					.getPlayer().getId() != player.getId()) {
				return false;
			}
		}

		// Wenn checker auf black bar ist (point=BLACK_BAR), dann muss der
		// targetPoint
		// zwischen 19 und 24 liegen
		if (checker.getPoint().getInternId() == BLACK_BAR) {
			if (player.getColor() != Color.BLACK
					|| targetPoint.getInternId() > BLACK_BAR
					|| targetPoint.getInternId() < 19) {
				return false;
			}
		}

		// Wenn checker auf white bar ist (point=WHITE_BAR), dann muss der
		// targetPoint
		// zwischen 1 und 6 liegen
		if (checker.getPoint().getInternId() == WHITE_BAR) {
			if (player.getColor() != Color.WHITE
					|| targetPoint.getInternId() > 6
					|| targetPoint.getInternId() < 1) {
				return false;
			}
		}

		// Wenn target Point auf einen Stack zeigt, müssen alle Checker auf dem
		// jeweiligem Homeboard sein
		if (targetPoint.getInternId() > BLACK_BAR) {
			Board board = game.getBoard();
			if (player.getColor() == Color.BLACK) {
				for (Point point : board.getPointList()) {
					if (point.getInternId() > 6
							&& point.getCheckerList().size() > 0) {
						return false;
					}
				}
			} else {
				for (Point point : board.getPointList()) {
					if (point.getInternId() < 19
							&& point.getCheckerList().size() > 0) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public Movement initializeMovement(Player player, Game game, Date created) {
		Movement movement = (Movement) applicationContext
				.getBean("movementImpl");
		movement.setCreated(created);
		movement.setGame(game);
		movement.setPlayer(player);

		movementDao.save(movement);

		return movement;
	}

	private byte getDiceValue(byte sourcePoint, byte targetPoint) {
		if (sourcePoint < WHITE_BAR && targetPoint < WHITE_BAR) {
			return (byte) Math.abs(targetPoint - sourcePoint);
		}

		if (sourcePoint < WHITE_BAR && targetPoint > 24) {
			return (byte) ((Math.abs(targetPoint - sourcePoint)) + 1);
		}

		if (sourcePoint > 24) {
			if (targetPoint > 6) {
				return (byte) (WHITE_BAR - targetPoint);
			}
			return targetPoint;
		}

		return 0;
	}

	public Movement getLastMovement(Game game) {
		return movementDao.getLast(game);
	}

	private Dice getDice(Set<Dice> dices, byte diceValue) {
		for (Dice dice : dices) {
			if (diceValue == dice.getValue() && !dice.isPlayed()) {
				return dice;
			}
		}
		return null;
	}
}
