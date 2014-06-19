package controllers;

import java.util.List;

import model.dto.GameDto;

import org.springframework.beans.factory.annotation.Autowired;

import play.libs.Json;
import play.mvc.Result;
import service.GameService;
import service.impl.exception.NoSystemUserException;
import actions.BasicAuth;
import backgammon04.model.Game;

@org.springframework.stereotype.Controller
public class GameController extends AbstractController {

	@Autowired
	private GameService gameService;

	public Result getGame(long id) {
		Game game = gameService.getGame(id);
		return ok(Json.toJson(gameService.assembly(game)));
	}

	public Result getOpenGames() {
		List<Game> games = gameService.getOpenGames();
		return ok(Json.toJson(gameService.assembly(games)));
	}

	public Result getStartetGames() {
		List<Game> games = gameService.getOpenGames(getUser());
		return ok(Json.toJson(gameService.assembly(games)));
	}

	@BasicAuth
	public Result startGame() {
		Game game = gameService.startGame(getUser());
		return ok(Json.toJson(gameService.assembly(game)));
	}

	@BasicAuth
	public Result joinGame(long id) {
		GameDto gameDto;
		try {
			gameDto = gameService.join(getUser(), id);
		} catch (NoSystemUserException e) {
			return forbidden();
		}
		return ok(Json.toJson(gameDto));
	}

	@BasicAuth
	public Result deleteGame(long id) {
		gameService.delete(id);
		return created();
	}

	@BasicAuth
	public Result getMyGames() {
		return ok(Json.toJson(gameService.getMyGames(getUser())));
	}

}
