package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.dto.CheckerDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import service.CheckerService;
import service.PlayerService;
import service.PointService;
import backgammon04.model.Checker;
import backgammon04.model.Player;
import backgammon04.model.Point;
import backgammon04.model.dao.CheckerDao;

@Service
public class CheckerServiceImpl implements CheckerService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private CheckerDao checkerDao;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private PointService pointService;

	@Override
	public Checker createChecker(Player player, Point point) {
		Checker checker = (Checker) applicationContext.getBean("checkerImpl");
		checker.setPlayer(player);
		checker.setPoint(point);

		checkerDao.save(checker);

		return checker;
	}

	@Override
	public CheckerDto assembly(Checker checker) {
		CheckerDto checkerDto = new CheckerDto();
		checkerDto.setId(checker.getId());
		checkerDto.setInternId(checker.getInternId());
		checkerDto.setPlayer(playerService.assembly(checker.getPlayer()));
		return checkerDto;
	}

	@Override
	public List<CheckerDto> assembly(Set<Checker> checkerList) {

		List<CheckerDto> checkerDtoList = new ArrayList<CheckerDto>();
		for (Checker checker : checkerList) {
			checkerDtoList.add(assembly(checker));
		}
		return checkerDtoList;
	}

	@Override
	public Checker save(Checker checker) {
		checkerDao.save(checker);
		return checker;
	}
}
