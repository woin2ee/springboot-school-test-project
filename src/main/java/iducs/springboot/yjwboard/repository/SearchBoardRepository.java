package iducs.springboot.yjwboard.repository;

import iducs.springboot.yjwboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    BoardEntity searchBoard();
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
