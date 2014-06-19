package service.impl;

import model.dto.BoardDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import service.BoardService;
import service.CheckerService;
import service.PointService;
import backgammon04.model.Board;
import backgammon04.model.Game;
import backgammon04.model.Player;
import backgammon04.model.dao.BoardDao;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private PointService pointService;

	@Autowired
	private CheckerService checkerService;

	@Override
	public Board initializeBoard(Game game, Player playerWhite,
			Player playerBlack) {
		Board board = (Board) applicationContext.getBean("boardImpl");
		board.setGame(game);
		board.setId(game.getId());
		boardDao.save(board);

		// Create points
		// 25. white bar
		// 26. black bar
		// 27. white stack
		// 28. black stack
		for (int i = 0; i < 28; i++) {
			pointService.createPoint(board, (byte) (i + 1));
		}

		// Create checker of white player
		// Point 1 => 2 checkers
		for (int i = 0; i < 2; i++) {
			checkerService.createChecker(playerWhite,
					pointService.getPoint(board, (byte) 1));
		}

		// Point 12 => 5 checkers
		for (int i = 0; i < 5; i++) {
			checkerService.createChecker(playerWhite,
					pointService.getPoint(board, (byte) 12));
		}

		// Point 17 => 3 checkers
		for (int i = 0; i < 3; i++) {
			checkerService.createChecker(playerWhite,
					pointService.getPoint(board, (byte) 17));
		}

		// Point 19 => 5 checkers
		for (int i = 0; i < 5; i++) {
			checkerService.createChecker(playerWhite,
					pointService.getPoint(board, (byte) 19));
		}

		// Create checker of black player
		// Point 24 => 2 checkers
		for (int i = 0; i < 2; i++) {
			checkerService.createChecker(playerBlack,
					pointService.getPoint(board, (byte) 24));
		}

		// Point 13 => 5 checkers
		for (int i = 0; i < 5; i++) {
			checkerService.createChecker(playerBlack,
					pointService.getPoint(board, (byte) 13));
		}

		// Point 8 => 3 checkers
		for (int i = 0; i < 3; i++) {
			checkerService.createChecker(playerBlack,
					pointService.getPoint(board, (byte) 8));
		}

		// Point 6 => 5 checkers
		for (int i = 0; i < 5; i++) {
			checkerService.createChecker(playerBlack,
					pointService.getPoint(board, (byte) 6));
		}

		return board;
	}

	@Override
	public BoardDto assembly(Board board) {
		BoardDto boardDto = new BoardDto();
		boardDto.setId(board.getId());
		boardDto.setPointList(pointService.assembly(board.getPointList()));
		return boardDto;
	}
}
