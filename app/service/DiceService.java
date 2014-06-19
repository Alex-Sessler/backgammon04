package service;

import java.util.List;
import java.util.Set;

import model.dto.DiceDto;
import service.impl.exception.ForbiddenMoveException;
import backgammon04.model.Dice;
import backgammon04.model.User;

public interface DiceService {

	public List<DiceDto> assembly(Set<Dice> diceList);

	public DiceDto assembly(Dice dice);

	public List<DiceDto> playDice(long gameId, User user)
			throws ForbiddenMoveException;

	public void save(Dice dice);
}
