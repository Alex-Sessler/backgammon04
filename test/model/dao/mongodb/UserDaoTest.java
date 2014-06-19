package model.dao.mongodb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import backgammon04.backgammon04_persistence_mysql.model.impl.UserImpl;
import backgammon04.model.User;
import backgammon04.model.dao.UserDao;

@Ignore
@RunWith(value = Parameterized.class)
@ContextConfiguration("conf/application-context.xml")
public class UserDaoTest {

	@Autowired
	@Qualifier("mongoUserDao")
	private UserDao mongoDbUserDao;

	private Class daoClass;
	private UserDao dao;

	public UserDaoTest(Class cl) {
		daoClass = cl;
	}

	@Before
	public void setUp() throws IllegalAccessException, InstantiationException {
		// dao = (UserDao) daoClass.newInstance();
		//
		// User user = new UserImpl();
		// user.setEmail("test@test.de");
		// user.setPassword("test");
		// user.setUsername("test");
		// user.setId(1L);
		// mongoTemplate.insert(user);
	}

	@Test
	public void test1() {
		User user = new UserImpl();
		user.setEmail("test@test.de");
		user.setPassword("test");
		user.setUsername("test");
		user.setId(1L);

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"test/conf/application-context.xml");

		mongoDbUserDao.save(user);

		Assert.assertNotNull(dao);
		Assert.assertTrue(false);
	}

}
