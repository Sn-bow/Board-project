package com.mysite.board.siteCmt;


import com.mysite.board.siteBoard.Board;
import com.mysite.board.siteUser.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String comment;

    private LocalDateTime regDate;

    @ManyToOne
    private Board board;

    @ManyToOne
    private SiteUser siteUser;
}
