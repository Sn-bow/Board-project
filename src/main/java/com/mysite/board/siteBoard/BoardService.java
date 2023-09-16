package com.mysite.board.siteBoard;


import com.mysite.board.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> boardList () {
        return this.boardRepository.findAll();
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

}
