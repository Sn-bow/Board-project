package com.mysite.board.siteBoard;


import com.mysite.board.DataNotFoundException;
import com.mysite.board.siteUser.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<Board> boardList (int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("regDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.boardRepository.findAll(pageable);
    }

    public Board detail (Integer boardId) {
        Optional<Board> _board = this.boardRepository.findById(boardId);
        if(_board.isPresent()) {
            return _board.get();
        } else {
            throw new DataNotFoundException("board is empty");
        }
    }

    public Board NextPost (Integer boardId) {
        Optional<Board> _nextPost = this.boardRepository.findByNextBoard(boardId);
        return _nextPost.orElseGet(Board::new);
    }
    public Board prevPost (Integer boardId) {
        Optional<Board> _prevPost = this.boardRepository.findByPrevBoard(boardId);
        return _prevPost.orElseGet(Board::new);
    }

    public void hitUpdate (Integer boardId) {
        Optional<Board> _board = this.boardRepository.findById(boardId);
        if(_board.isPresent()) {
            Board board = _board.get();
            board.setHit(board.getHit() + 1);
            this.boardRepository.save(board);
        } else {
            throw new DataNotFoundException("board is empty");
        }
    }

    public void create (BoardForm boardForm, SiteUser siteUser) {
        Board board = new Board();
        board.setSubject(boardForm.getSubject());
        board.setContent(boardForm.getContent());
        board.setRegDate(LocalDateTime.now());
        board.setHit(0);
        board.setSiteUser(siteUser);
        this.boardRepository.save(board);
    }

}
