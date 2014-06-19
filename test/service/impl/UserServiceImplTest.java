package service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import service.UserService;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/conf/application-context.xml")
public class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Test
	public void test() {
		userService.getUser("alex");
	}

}
