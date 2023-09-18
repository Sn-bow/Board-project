package com.mysite.board.siteCmt;


import com.mysite.board.siteBoard.Board;
import com.mysite.board.siteBoard.BoardService;
import com.mysite.board.siteUser.SiteUser;
import com.mysite.board.siteUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {


    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create (@RequestParam String comment, @PathVariable("id") Integer id, Principal principal) {
        SiteUser user = this.userService.getUser(principal.getName());
        Board board = this.boardService.detail(id);
        this.commentService.create(comment, board, user);
        return String.format("redirect:/board/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String delete (@RequestParam Integer cmtId, @PathVariable("id") Integer id) {
        this.commentService.delete(cmtId);
        return String.format("redirect:/board/detail/%s", id);
    }

}
