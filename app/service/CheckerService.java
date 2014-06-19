package service;

import java.util.List;
import java.util.Set;

import model.dto.CheckerDto;
import backgammon04.model.Checker;
import backgammon04.model.Player;
import backgammon04.model.Point;

public interface CheckerService {
	public Checker createChecker(Player player, Point point);

	public List<CheckerDto> assembly(Set<Checker> checkerList);

	public CheckerDto assembly(Checker checker);

	public Checker save(Checker checker);
}
