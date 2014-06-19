package controllers;

import org.springframework.beans.factory.annotation.Autowired;

import play.libs.Json;
import play.mvc.Result;
import service.DiceService;
import service.impl.exception.ForbiddenMoveException;
import actions.BasicAuth;

@org.springframework.stereotype.Controller
public class DiceController extends AbstractController {

	@Autowired
	private DiceService diceService;
	
	@BasicAuth
    public Result playDice(long gameId) {
    	try {
			return ok(Json.toJson(diceService.playDice(gameId, getUser())));
		} catch (ForbiddenMoveException e) {
			return forbidden(e.getMessage());
		}
    }
    
}
