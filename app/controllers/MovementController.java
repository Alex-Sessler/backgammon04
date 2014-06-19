package controllers;

import org.springframework.beans.factory.annotation.Autowired;

import play.libs.Json;
import play.mvc.Result;
import service.DiceService;
import service.GameService;
import service.MovementService;
import service.impl.exception.ForbiddenMoveException;
import actions.BasicAuth;
import backgammon04.model.Game;

@org.springframework.stereotype.Controller
public class MovementController extends AbstractController {

	@Autowired
	private MovementService movementService;

	@Autowired
	private DiceService diceService;

	@Autowired
	private GameService gameService;

	@BasicAuth
	public Result move(long gameId, int sourcePointInternalId,
			int targetPointInternalId) {
		try {
			movementService.move(getUser(), gameId,
					(byte) sourcePointInternalId, (byte) targetPointInternalId);
		} catch (ForbiddenMoveException e) {
			return forbidden();
		}

		Game game = gameService.getGame(gameId);
		return ok(Json.toJson(gameService.assembly(game)));
	}
}
