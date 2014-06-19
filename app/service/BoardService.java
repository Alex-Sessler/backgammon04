package service;

import model.dto.BoardDto;
import backgammon04.model.Board;
import backgammon04.model.Game;
import backgammon04.model.Player;

public interface BoardService {

	public Board initializeBoard(Game game, Player playerWhite,
			Player playerBlack);

	public BoardDto assembly(Board board);
}
