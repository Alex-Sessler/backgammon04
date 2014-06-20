package service.impl;

import model.dto.RegisterDto;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import backgammon04.model.dao.UserDao;
import service.UserService;
import service.impl.exception.RegistrationNotPermittedException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/conf/application-context.xml")
public class UserServiceImplTest {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserService userService;

	@Test
	public void registerUserTest() throws RegistrationNotPermittedException {
		
		RegisterDto dto = new RegisterDto();
		dto.setEmail("user1@test.de");
		dto.setPassword1("test1");
		dto.setPassword2("test1");
		dto.setUsername("test1");
		userService.register(dto);
		Assert.assertNotNull(userService.getUser("test1"));
		userDao.delete(userDao.getUser("test1"));
		Assert.assertNull(userService.getUser("test1"));
	}

}
