package controllers;

import org.springframework.beans.factory.annotation.Autowired;

import play.mvc.Controller;
import service.UserService;
import backgammon04.model.User;

@org.springframework.stereotype.Controller
public class AbstractController extends Controller {

	@Autowired
	private UserService userService;

	protected User getUser() {
		String userName = session().get("username");
		return userService.getUser(userName);
	}
}
