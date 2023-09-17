package com.mysite.board;

import com.mysite.board.siteBoard.Board;
import com.mysite.board.siteBoard.BoardRepository;
import com.mysite.board.siteBoard.BoardService;
import com.mysite.board.siteUser.SiteUser;
import com.mysite.board.siteUser.UserRepository;
import com.mysite.board.siteUser.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardRepository boardRepository;

	@Test
	void userLoginTest() {
		SiteUser user = this.userService.getUser("test1");
		for(int i = 1; i <= 300; i++) {
			Board board = new Board();
			board.setHit(0);
			board.setSiteUser(user);
			board.setSubject("test" + i);
			board.setContent("test" + i);
			board.setRegDate(LocalDateTime.now());
			this.boardRepository.save(board);
		}

	}

}
