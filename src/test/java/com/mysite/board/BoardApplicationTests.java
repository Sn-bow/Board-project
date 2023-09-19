package com.mysite.board;

import com.mysite.board.siteBoard.Board;
import com.mysite.board.siteBoard.BoardRepository;
import com.mysite.board.siteBoard.BoardService;
import com.mysite.board.siteFile.FileEntity;
import com.mysite.board.siteUser.SiteUser;
import com.mysite.board.siteUser.UserRepository;
import com.mysite.board.siteUser.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardRepository boardRepository;

	@Test
	@Transactional
	void userLoginTest() {
		Board post = this.boardService.detail(334);
		System.out.println("testing start");
		for(FileEntity f : post.getFileEntityList()) {
			System.out.println("testtesttest" + f.getOrgNm());
		}
		System.out.println("testing end");
	}

}
