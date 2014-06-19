package service;

import java.util.List;

import model.dto.GameDto;
import service.impl.exception.NoSystemUserException;
import backgammon04.model.Game;
import backgammon04.model.User;

public interface GameService {

	public Game startGame(User user);

	public Game getGame(long id);

	public void save(Game game);

	public GameDto assembly(Game game);

	public GameDto join(User user, long gameId) throws NoSystemUserException;

	public String getCurrentPlayer(long gameId);

	public void delete(long id);

	public List<Game> getOpenGames();

	public List<Game> getOpenGames(User user);

	public List<GameDto> assembly(List<Game> games);

	public List<GameDto> getMyGames(User user);
}
