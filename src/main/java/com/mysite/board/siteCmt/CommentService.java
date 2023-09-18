package com.mysite.board.siteCmt;


import com.mysite.board.DataNotFoundException;
import com.mysite.board.siteBoard.Board;
import com.mysite.board.siteUser.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void create (String comment, Board board, SiteUser siteUser) {
        Comment cmt = new Comment();
        cmt.setComment(comment);
        cmt.setBoard(board);
        cmt.setRegDate(LocalDateTime.now());
        cmt.setSiteUser(siteUser);
        this.commentRepository.save(cmt);
    }

    public void delete (Integer cmtId) {
        Optional<Comment> comment = this.commentRepository.findById(cmtId);
        if (comment.isPresent()) {
            this.commentRepository.delete(comment.get());
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

}
