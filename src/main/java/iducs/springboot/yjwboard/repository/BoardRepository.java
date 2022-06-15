package iducs.springboot.yjwboard.repository;

import iducs.springboot.yjwboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Query("select b, w from BoardEntity b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b, w, count(r) " +
            "from BoardEntity b left join b.writer w " +
            "left join ReplyEntity r on r.board = b where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);

    @Query(value = "select b, w, count(r) " +
            "from BoardEntity b left join b.writer w " +
            "left join ReplyEntity r on r.board = b " +
            "group by b",
            countQuery = "select count(b) from BoardEntity b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);
}
