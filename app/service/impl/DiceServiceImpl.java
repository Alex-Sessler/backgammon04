package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import model.dto.DiceDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import service.DiceService;
import service.GameService;
import service.MovementService;
import service.PlayerService;
import service.impl.exception.ForbiddenMoveException;
import backgammon04.model.Dice;
import backgammon04.model.Game;
import backgammon04.model.Movement;
import backgammon04.model.Player;
import backgammon04.model.User;
import backgammon04.model.dao.DiceDao;
import backgammon04.model.dao.MovementDao;

@Service
public class DiceServiceImpl implements DiceService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private DiceDao diceDao;

	@Autowired
	private MovementService movementService;

	@Autowired
	private MovementDao movementDao;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private GameService gameService;

	@Override
	public DiceDto assembly(Dice dice) {
		DiceDto diceDto = new DiceDto(dice.getValue(), dice.isPlayed());
		return diceDto;
	}

	@Override
	public List<DiceDto> assembly(Set<Dice> diceList) {
		List<DiceDto> diceDtoList = new ArrayList<DiceDto>();

		for (Dice dice : diceList) {
			diceDtoList.add(assembly(dice));
		}
		return diceDtoList;
	}

	@Override
	public List<DiceDto> playDice(long gameId, User user)
			throws ForbiddenMoveException {
		Game game = gameService.getGame(gameId);
		Player player = playerService.getPlayer(game, user);

		if (game.getEnded() != null) {
			throw new ForbiddenMoveException("Game is over!");
		}

		if (game.getColor() != player.getColor()) {
			throw new ForbiddenMoveException("Not your turn. Please wait!");
		}

		Movement lastMovement = movementService.getLastMovement(game);

		if (lastMovement != null) {
			for (Dice dice : lastMovement.getDices()) {
				if (!dice.isPlayed()) {
					throw new ForbiddenMoveException(
							"Not allowed to play dice again!");
				}
			}
		}

		Movement movement = movementService.initializeMovement(player, game,
				new Date());
		Set<Dice> diceList = new HashSet<Dice>();

		Dice firstDice = (Dice) applicationContext.getBean("diceImpl");
		firstDice.setMovement(movement);
		firstDice.setValue(getDiceNumber());
		diceDao.save(firstDice);
		diceList.add(firstDice);

		Dice secondDice = (Dice) applicationContext.getBean("diceImpl");
		secondDice.setMovement(movement);
		secondDice.setValue(getDiceNumber());
		diceDao.save(secondDice);
		diceList.add(secondDice);

		if (firstDice.getValue() == secondDice.getValue()) {
			Dice thirdDice = (Dice) applicationContext.getBean("diceImpl");
			thirdDice.setMovement(movement);
			thirdDice.setValue(firstDice.getValue());
			diceDao.save(thirdDice);
			diceList.add(thirdDice);

			Dice fourthDice = (Dice) applicationContext.getBean("diceImpl");
			fourthDice.setMovement(movement);
			fourthDice.setValue(firstDice.getValue());
			diceDao.save(fourthDice);
			diceList.add(fourthDice);
		}

		return assembly(diceList);
	}

	private byte getDiceNumber() {
		Random generator = new Random();
		return (byte) (generator.nextInt(6) + 1);
	}

	@Override
	public void save(Dice dice) {
		diceDao.save(dice);
	}
}
