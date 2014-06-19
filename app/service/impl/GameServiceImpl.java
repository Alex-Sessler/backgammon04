package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.dto.GameDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import service.BoardService;
import service.DiceService;
import service.GameService;
import service.MovementService;
import service.PlayerService;
import service.UserService;
import service.impl.exception.NoSystemUserException;
import backgammon04.model.Game;
import backgammon04.model.Movement;
import backgammon04.model.Player;
import backgammon04.model.User;
import backgammon04.model.dao.GameDao;
import backgammon04.util.Color;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private GameDao gameDao;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private MovementService movementService;

	@Autowired
	private DiceService diceService;

	@Override
	public Game startGame(User user) {
		Game game = (Game) applicationContext.getBean("gameImpl");
		game.setInitialized(new Date());
		gameDao.save(game);

		// Create first player => initializer
		Player playerBlack = playerService
				.createPlayer(game, user, Color.BLACK);

		// Create system user until a real user is joining the game
		Player playerWhite = playerService
				.createPlayer(game, null, Color.WHITE);

		// Initialize board with default points and starting checkers
		boardService.initializeBoard(game, playerWhite, playerBlack);

		return getGame(game.getId());
	}

	@Override
	public Game getGame(long id) {
		return gameDao.get(id);
	}

	@Override
	public List<GameDto> assembly(List<Game> games) {
		List<GameDto> gameDtoList = new ArrayList<GameDto>();
		for (Game game : games) {
			gameDtoList.add(assembly(game));
		}
		return gameDtoList;
	}

	@Override
	public GameDto assembly(Game game) {
		GameDto gameDto = new GameDto();
		gameDto.setBoard(boardService.assembly(game.getBoard()));
		gameDto.setId(game.getId());
		gameDto.setInitialized(game.getInitialized());
		gameDto.setPlayers(playerService.assembly(game.getPlayers()));
		gameDto.setStarted(game.getStarted());
		gameDto.setEnded(game.getEnded());
		gameDto.setCurrentPlayer(game.getColor());
		Movement movement = movementService.getLastMovement(game);
		if (movement != null
				&& movement.getPlayer().getColor() == game.getColor()
				&& movement.getDices() != null) {
			gameDto.setDices(diceService.assembly(movement.getDices()));
		}

		return gameDto;
	}

	@Override
	public GameDto join(User user, long gameId) throws NoSystemUserException {
		Game game = gameDao.get(gameId);

		for (Player player : game.getPlayers()) {
			if (player.getUser() == null) {
				player.setUser(user);
				playerService.update(player);
				game.setStarted(new Date());
				game.setColor(Color.BLACK);
				gameDao.save(game);
				return assembly(game);
			}
		}

		throw new NoSystemUserException();

	}

	@Override
	public void delete(long id) {
		gameDao.delete(getGame(id));
	}

	@Override
	public String getCurrentPlayer(long gameId) {
		Color color;
		if ((color = getGame(gameId).getColor()) == null) {
			return "none";
		}
		return color.toString();
	}

	@Override
	public void save(Game game) {
		gameDao.save(game);
	}

	@Override
	public List<Game> getOpenGames() {
		return gameDao.getOpenGames();
	}

	@Override
	public List<Game> getOpenGames(User user) {
		return gameDao.getOpenGames(user);
	}

	@Override
	public List<GameDto> getMyGames(User user) {
		return assembly(gameDao.getMyGames(user));
	}

}
