package com.mysite.board.siteBoard;


import com.mysite.board.siteUser.SiteUser;
import com.mysite.board.siteUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @GetMapping("/list")
    public String list (Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Board> boardList = this.boardService.boardList(page);
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


//     수정 필요
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify (@PathVariable("id")Integer id, BoardForm boardForm, Principal principal) {
        Board board = this.boardService.detail(id);
        if(!board.getSiteUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        boardForm.setSubject(board.getSubject());
        boardForm.setContent(board.getContent());
        return "board_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyP (@PathVariable("id") Integer id, BoardForm boardForm, Principal principal) {
        Board board = this.boardService.detail(id);
        if (!board.getSiteUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.boardService.modify(board, boardForm.getSubject(), boardForm.getContent());
        return "redirect:/board/list";
    } // 수정 필요

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create (BoardForm boardForm) {
        return "board_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create (BoardForm boardForm, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.boardService.create(boardForm, siteUser);
        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String delete (@PathVariable("id") Integer id) {
        this.boardService.delete(id);
        return "redirect:/board/list";
    }

}
