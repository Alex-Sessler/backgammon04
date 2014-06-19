package model.dto;

import java.util.Date;
import java.util.List;

import backgammon04.util.Color;

public class GameDto {

	private long id;

	private List<PlayerDto> players;

	private Date initialized;

	private Date started;

	private Date ended;

	private BoardDto board;

	private Color currentPlayer;

	private List<DiceDto> dices;

	public GameDto(long id, List<PlayerDto> players, Date initialized,
			Date started, BoardDto board, List<DiceDto> dices) {
		this.id = id;
		this.players = players;
		this.initialized = initialized;
		this.started = started;
		this.board = board;
		this.dices = dices;
	}

	public GameDto() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<PlayerDto> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerDto> players) {
		this.players = players;
	}

	public Date getInitialized() {
		return initialized;
	}

	public void setInitialized(Date initialized) {
		this.initialized = initialized;
	}

	public Date getStarted() {
		return started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public BoardDto getBoard() {
		return board;
	}

	public void setBoard(BoardDto board) {
		this.board = board;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Color currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Date getEnded() {
		return ended;
	}

	public void setEnded(Date ended) {
		this.ended = ended;
	}

	public List<DiceDto> getDices() {
		return dices;
	}

	public void setDices(List<DiceDto> dices) {
		this.dices = dices;
	}
}
