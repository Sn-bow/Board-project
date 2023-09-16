package com.mysite.board;

import com.mysite.board.siteBoard.Board;
import com.mysite.board.siteBoard.BoardService;
import com.mysite.board.siteUser.SiteUser;
import com.mysite.board.siteUser.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BoardService boardService;

	@Test
	void userLoginTest() {
		Board nPost = this.boardService.NextPost(2);
		System.out.println(nPost.getId());
		Board pPost = this.boardService.prevPost(1);
		System.out.println(pPost.getId());
	}

}
