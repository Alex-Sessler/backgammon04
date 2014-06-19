package service;

import java.util.List;
import java.util.Set;

import model.dto.PointDto;
import backgammon04.model.Board;
import backgammon04.model.Point;

public interface PointService {

	public Point createPoint(Board board, byte internalId);

	public Point getPoint(Board board, byte internId);

	public List<PointDto> assembly(Set<Point> pointList);

	public PointDto assembly(Point point);
}
