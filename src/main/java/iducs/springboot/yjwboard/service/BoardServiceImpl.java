package iducs.springboot.yjwboard.service;

import iducs.springboot.yjwboard.domain.Board;
import iducs.springboot.yjwboard.domain.PageRequestDTO;
import iducs.springboot.yjwboard.domain.PageResultDTO;
import iducs.springboot.yjwboard.entity.BoardEntity;
import iducs.springboot.yjwboard.entity.MemberEntity;
import iducs.springboot.yjwboard.repository.BoardRepository;
import iducs.springboot.yjwboard.repository.ReplyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public BoardServiceImpl(BoardRepository boardRepository, ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }


    @Override
    public Long register(Board board) {
        log.info("board register : " + board);
        BoardEntity boardEntity = dtoToEntity(board);
        boardRepository.save(boardEntity);
        return boardEntity.getBno();
    }

    @Override
    public PageResultDTO<Board, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(">>>>>" + pageRequestDTO);
        Function<Object[], Board> fn =
                (entities -> entityToDto((BoardEntity) entities[0], (MemberEntity) entities[1], (Long) entities[2]));
        Page<Object[]> result =
                boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public Board getById(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] en = (Object[]) result;
        return entityToDto((BoardEntity) en[0], (MemberEntity) en[1], (Long) en[2]);
    }

    @Override
    public Long modify(Board board) {
        Optional<BoardEntity> result = boardRepository.findById(board.getBno());
        BoardEntity boardEntity = null;
        if (result.isPresent()) {
            boardEntity = (BoardEntity) result.get();
            boardEntity.changeTitle(board.getTitle());
            boardEntity.changeContent(board.getContent());
            boardRepository.save(boardEntity);
        }
        assert boardEntity != null;
        return boardEntity.getBno();
    }

    @Transactional
    @Override
    public void deleteWithRepliesById(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }
}
