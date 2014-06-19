package service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import service.BoardService;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/conf/application-context.xml")
public class BoardServiceImplTest {

	@Autowired
	private BoardService boardService;

	@Test
	public void test() {
		boardService.initializeBoard(null, null, null);
	}

}
