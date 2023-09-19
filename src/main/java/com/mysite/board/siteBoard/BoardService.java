package com.mysite.board.siteBoard;


import com.mysite.board.DataNotFoundException;
import com.mysite.board.siteCmt.Comment;
import com.mysite.board.siteFile.FileEntity;
import com.mysite.board.siteFile.FileService;
import com.mysite.board.siteUser.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileService fileService;


    private Specification<Board> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Board, SiteUser> u1 = b.join("siteUser", JoinType.LEFT);
                return cb.or(cb.like(u1.get("username"), "%" + kw + "%"));
            }
        };
    }


    public Page<Board> boardList (String query, String kw, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("regDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        if(query.equals("subject")) {
            return this.boardRepository.findBySubjectContaining(kw, pageable);
        } else if (query.equals("content")) {
            return this.boardRepository.findByContentContaining(kw, pageable);
        } else if (query.equals("user")) {
            Specification<Board> spec = search(kw);
            return this.boardRepository.findAll(spec, pageable);
        } else if (query.equals("subject+content")) {
            return this.boardRepository.findBySubjectOrContentContaining(kw, kw, pageable);
        }
        else {
            return this.boardRepository.findAll(pageable);
        }
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

    public void create (BoardForm boardForm, SiteUser siteUser, List<MultipartFile> files){
        Board board = new Board();
        List<FileEntity> fileEntityList = new ArrayList<>();
        board.setSubject(boardForm.getSubject());
        board.setContent(boardForm.getContent());
        board.setRegDate(LocalDateTime.now());
        board.setHit(0);
        board.setSiteUser(siteUser);
        board.setFileEntityList(fileEntityList);
        this.boardRepository.save(board);

        try {
            for(MultipartFile file : files) {
                fileEntityList.add(this.fileService.saveFile(file, board));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete (Integer id) {
        Optional<Board> board = this.boardRepository.findById(id);
        if(board.isPresent()) {
            this.boardRepository.delete(board.get());
        } else {
            throw new DataNotFoundException("board not found");
        }
    }

    public void modify (Board board, String subject, String content) {
        board.setSubject(subject);
        board.setContent(content);
        this.boardRepository.save(board);
    }

}
