package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.signin;
import views.html.index;
import views.html.profile;
import views.html.games;
import views.html.opengames;
import views.html.game;
import views.html.register;

public class Application extends Controller {

	public static Result signin() {
		return ok(signin.render(""));
	}
	
	public static Result index() {
		return ok(index.render(""));
	}

	public static Result profile() {
		return ok(profile.render("Profile"));
	}
	
	public static Result games() {
		return ok(games.render("Games"));
	}
	
	public static Result openGames() {
		return ok(opengames.render("Open Games"));
	}
	
	public static Result game(int id) {
		return ok(game.render(String.valueOf(id)));
	}
	
//	
//	public static Result menu() {
//		return ok(menu.render("Hallo"));
//	}
//
	public static Result register() {
		return ok(register.render("Register"));
	}

}
