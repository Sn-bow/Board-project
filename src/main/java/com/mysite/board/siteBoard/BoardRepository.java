package com.mysite.board.siteBoard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query(value = "SELECT * FROM BOARD " +
            "WHERE REG_DATE > (SELECT REG_DATE FROM BOARD WHERE ID = :id) " +
            "LIMIT 1", nativeQuery = true)
    Optional<Board> findByNextBoard(Integer id);

    @Query(value = "select * from board " +
            "where reg_date < (select reg_date from board where id = :id) order by reg_date desc " +
            "LIMIT 1", nativeQuery = true)
    Optional<Board> findByPrevBoard(Integer id);

    Page<Board> findAll(Pageable pageable);
}
