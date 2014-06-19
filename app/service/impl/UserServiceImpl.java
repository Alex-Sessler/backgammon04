package service.impl;

import java.util.ArrayList;
import java.util.List;

import model.dto.RegisterDto;
import model.dto.UpdateUserDto;
import model.dto.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import service.UserService;
import service.impl.exception.RegistrationNotPermittedException;
import backgammon04.model.User;
import backgammon04.model.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private UserDao userDao;

	@Override
	public User getSystemUser() {
		User systemUser = userDao.getUser("system");
		if (systemUser == null) {
			systemUser = (User) applicationContext.getBean("userImpl");
			systemUser.setEmail("system@sesebackgammon.de");
			systemUser.setPassword("sesebackgammon-passwd");
			systemUser.setUsername("system");

			userDao.save(systemUser);
		}

		return systemUser;
	}

	@Override
	public User getUser(String username) {
		return userDao.getUser(username);
	}

	@Override
	public UserDto assembly(User user) {
		if (user == null)
			return null;
		UserDto userDto = new UserDto(user.getId(), user.getUsername(),
				user.getEmail());
		return userDto;
	}

	@Override
	public List<UserDto> assembly(List<User> userList) {
		List<UserDto> userDtoList = new ArrayList<UserDto>();

		for (User user : userList) {
			userDtoList.add(assembly(user));
		}
		return userDtoList;
	}

	@Override
	public User getUser(long id) {
		return userDao.get(id);
	}

	@Override
	public void register(RegisterDto registerDto)
			throws RegistrationNotPermittedException {
		if (!registerDto.getPassword1().equals(registerDto.getPassword2())) {
			throw new RegistrationNotPermittedException(
					"Given passwords don't match!");
		}

		if (userDao.getUser(registerDto.getUsername()) != null) {
			throw new RegistrationNotPermittedException(
					"User with given username exists!");
		}
		User user = (User) applicationContext.getBean("userImpl");
		user.setEmail(registerDto.getEmail());
		user.setPassword(registerDto.getPassword1());
		user.setUsername(registerDto.getUsername());
		userDao.save(user);

		register2(registerDto);
	}

	@Override
	public User authenticate(String username, String password) {
		User user = getUser(username);
		if (user == null)
			return null;
		if (!user.getPassword().equals(password))
			return null;
		return user;
	}

	@Override
	public UserDto updateUser(User user, UpdateUserDto updateUserDto)
			throws RegistrationNotPermittedException {

		if (updateUserDto.getEmail() == null
				|| updateUserDto.getUsername() == null
				|| updateUserDto.getPassword() == null) {
			throw new RegistrationNotPermittedException("Missing data!");
		}

		if (!user.getUsername().equalsIgnoreCase(updateUserDto.getUsername())
				&& userDao.getUser(updateUserDto.getUsername()) != null) {
			throw new RegistrationNotPermittedException(
					"Username already exists!");
		}

		if (!user.getEmail().equalsIgnoreCase(updateUserDto.getEmail())
				&& userDao.getByEmail(updateUserDto.getEmail()) != null) {
			throw new RegistrationNotPermittedException("Email already exists!");
		}

		if (!user.getPassword().equalsIgnoreCase(updateUserDto.getPassword())) {
			throw new RegistrationNotPermittedException("Wrong password!");
		}

		if (!updateUserDto.getNewpassword1().equalsIgnoreCase(
				updateUserDto.getNewpassword2())) {
			throw new RegistrationNotPermittedException(
					"Passwords doesn't match!");
		}

		user.setEmail(updateUserDto.getEmail());
		user.setPassword(updateUserDto.getPassword());
		user.setUsername(updateUserDto.getUsername());
		userDao.save(user);

		return assembly(user);
	}

	@Override
	@Async
	public void register2(RegisterDto registerDto)
			throws RegistrationNotPermittedException {

		if (!registerDto.getPassword1().equals(registerDto.getPassword2())) {
			throw new RegistrationNotPermittedException(
					"Given passwords don't match!");
		}

		if (userDao.getUser(registerDto.getUsername()) != null) {
			throw new RegistrationNotPermittedException(
					"User with given username exists!");
		}
		User user = (User) applicationContext.getBean("userImpl");
		user.setEmail(registerDto.getEmail());
		user.setPassword(registerDto.getPassword1());
		user.setUsername(registerDto.getUsername());
		System.out.println("saved");
		userDao.save(user);

	}

}
