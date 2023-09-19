package com.mysite.board.siteBoard;

import com.mysite.board.siteUser.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
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

    Page<Board> findAll(Specification<Board> spec, Pageable pageable);

    Page<Board> findBySubjectContaining(String keyword, Pageable pageable);
    Page<Board> findByContentContaining(String keyword, Pageable pageable);

    Page<Board> findBySubjectOrContentContaining(String keyword1, String keyword2, Pageable pageable);

}
