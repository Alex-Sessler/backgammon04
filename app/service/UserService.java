package service;

import java.util.List;

import model.dto.RegisterDto;
import model.dto.UpdateUserDto;
import model.dto.UserDto;
import service.impl.exception.RegistrationNotPermittedException;
import backgammon04.model.User;

public interface UserService {
	public User getSystemUser();

	public User getUser(String username);

	public User getUser(long id);

	public UserDto assembly(User user);

	public List<UserDto> assembly(List<User> userList);

	public void register(RegisterDto registerDto)
			throws RegistrationNotPermittedException;

	public void register2(RegisterDto registerDto)
			throws RegistrationNotPermittedException;

	public User authenticate(String username, String password);

	public UserDto updateUser(User user, UpdateUserDto updateUserDto)
			throws RegistrationNotPermittedException;
}
