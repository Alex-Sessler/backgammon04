package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.dto.PointDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import service.CheckerService;
import service.PointService;
import backgammon04.model.Board;
import backgammon04.model.Checker;
import backgammon04.model.Point;
import backgammon04.model.dao.PointDao;

@Service
public class PointServiceImpl implements PointService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private PointDao pointDao;

	@Autowired
	private CheckerService checkerService;

	@Override
	public Point createPoint(Board board, byte internalId) {
		Point point = (Point) applicationContext.getBean("pointImpl");
		point.setBoard(board);
		point.setInternId(internalId);

		pointDao.save(point);

		return point;
	}

	@Override
	public Point getPoint(Board board, byte internId) {
		return pointDao.get(board, internId);
	}

	@Override
	public PointDto assembly(Point point) {
		PointDto pointDto = new PointDto();
		pointDto.setInternId(point.getInternId());
		if (point.getCheckerList() != null) {
			for (Checker checker : point.getCheckerList()) {
				pointDto.setColor(checker.getPlayer().getColor().toString());
				pointDto.setNumberOfCheckers(point.getCheckerList().size());
				break;
			}
		}

		return pointDto;
	}

	@Override
	public List<PointDto> assembly(Set<Point> pointList) {
		List<PointDto> poiDtoList = new ArrayList<PointDto>();
		for (Point point : pointList) {
			poiDtoList.add(assembly(point));
		}
		return poiDtoList;
	}

}
