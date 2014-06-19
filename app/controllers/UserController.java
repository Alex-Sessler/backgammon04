package controllers;

import static play.data.Form.form;
import model.dto.RegisterDto;
import model.dto.UpdateUserDto;
import model.dto.UserDto;

import org.springframework.beans.factory.annotation.Autowired;

import play.libs.Json;
import play.mvc.Result;
import service.UserService;
import service.impl.exception.RegistrationNotPermittedException;
import actions.BasicAuth;

@org.springframework.stereotype.Controller
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;

	@BasicAuth
	public Result login() {
		return ok(Json.toJson(userService.assembly(getUser())));
	}

	public Result getUser(long id) {
		return ok(Json.toJson(userService.assembly(userService.getUser(id))));
	}

	public Result createUser() {
		RegisterDto registerDto = form(RegisterDto.class).bindFromRequest()
				.get();
		try {
			userService.register2(registerDto);
		} catch (RegistrationNotPermittedException e) {
			forbidden(e.getMessage());
		}
		return ok();
	}

	@BasicAuth
	public Result updateUser() {
		UpdateUserDto updateUserDto = form(UpdateUserDto.class)
				.bindFromRequest().get();
		UserDto userDto = null;
		try {
			userDto = userService.updateUser(getUser(), updateUserDto);
		} catch (RegistrationNotPermittedException e) {
			badRequest(e.getMessage());
		}

		return ok(Json.toJson(userDto));
	}

}
