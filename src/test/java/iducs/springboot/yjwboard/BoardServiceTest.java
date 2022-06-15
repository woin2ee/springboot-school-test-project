package iducs.springboot.yjwboard;

import iducs.springboot.yjwboard.domain.Board;
import iducs.springboot.yjwboard.domain.PageRequestDTO;
import iducs.springboot.yjwboard.domain.PageResultDTO;
import iducs.springboot.yjwboard.entity.BoardEntity;
import iducs.springboot.yjwboard.repository.BoardRepository;
import iducs.springboot.yjwboard.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardServiceTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void testInitBoards() {
        IntStream.rangeClosed(1, 47).forEach(i -> {
            Long seqLong = (long) new Random().nextInt(50);
            seqLong = (seqLong == 0) ? 1 : seqLong;

            Board board = Board.builder()
                    .title("title" + i)
                    .content("Content...")
                    .writerSeq(seqLong)
                    .build();
            Long bno = boardService.register(board);
        });
    }

//    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
//        pageRequestDTO.setPage(2);
        PageResultDTO<Board, Object[]> result = boardService.getList(pageRequestDTO);
        for(Board dto : result.getDtoList())
            System.out.println(dto.getBno() + " : " + dto.getTitle());
    }

//    @Transactional
//    @Test
    public void testLazyLoading() {
        Optional<BoardEntity> result = boardRepository.findById(10L);
        BoardEntity boardEntity = result.get();
        System.out.println(boardEntity.getWriter());
    }

//    @Test
    public void testDeleteWithRepliesById() {
        Long bno = 1L;
        boardService.deleteWithRepliesById(bno);
    }
}
