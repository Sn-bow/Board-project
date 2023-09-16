package com.mysite.board.siteBoard;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list (Model model) {
        List<Board> boardList = this.boardService.boardList();
        model.addAttribute("list", boardList);
        return "list";
    }

    @GetMapping("/detail/{id}")
    public String detail (Model model, @PathVariable("id") Integer id) {
        this.boardService.hitUpdate(id);
        Board post = this.boardService.detail(id);
        Board nextPost = this.boardService.NextPost(id);
        Board prevPost = this.boardService.prevPost(id);
        model.addAttribute("post", post);
        model.addAttribute("nextPost", nextPost);
        model.addAttribute("prevPost", prevPost);
        return "detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create () {
        return "board_form";
    }

}
