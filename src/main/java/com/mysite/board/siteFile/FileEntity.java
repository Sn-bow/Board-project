package com.mysite.board.siteFile;

import com.mysite.board.siteBoard.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "file")
@Entity
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orgNm;

    private String savedNm;

    private String savedPath;

    @ManyToOne
    private Board board;


    @Builder
    public FileEntity(Long id, String orgNm, String savedNm, String savedPath, Board board) {
        this.id = id;
        this.orgNm = orgNm;
        this.savedNm = savedNm;
        this.savedPath = savedPath;
        this.board = board;
    }



}
