package com.mysite.board.siteBoard;


import com.mysite.board.siteFile.FileEntity;
import com.mysite.board.siteFile.FileRepository;
import com.mysite.board.siteFile.FileService;
import com.mysite.board.siteUser.SiteUser;
import com.mysite.board.siteUser.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;
    private final FileService fileService;
    private final FileRepository fileRepository;

    @GetMapping("/list")
    public String list (Model model,@RequestParam(value= "query", defaultValue = "subject") String query,@RequestParam(value = "kw", defaultValue = "") String kw , @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Board> boardList = this.boardService.boardList(query, kw, page);
        model.addAttribute("list", boardList);
        model.addAttribute("kw", kw);
        model.addAttribute("query", query);
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
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create (BoardForm boardForm) {
        return "board_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create (BoardForm boardForm, @RequestParam("files") List<MultipartFile> files, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.boardService.create(boardForm, siteUser, files);
        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public String delete (@PathVariable("id") Integer id) {
        this.boardService.delete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) throws MalformedURLException {
        FileEntity file = this.fileRepository.findById(id).orElse(null);
        UrlResource resource = new UrlResource("file:" + file.getSavedPath());
        String encodedFileName = UriUtils.encode(file.getOrgNm(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }

}
