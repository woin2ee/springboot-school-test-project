package iducs.springboot.yjwboard.service;

import iducs.springboot.yjwboard.domain.BoardDTO;
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
    public Long register(BoardDTO boardDTO) {
        log.info("board register : " + boardDTO);
        BoardEntity boardEntity = dtoToEntity(boardDTO);
        boardRepository.save(boardEntity);
        return boardEntity.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(">>>>>" + pageRequestDTO);
        Function<Object[], BoardDTO> fn =
                (entities -> entityToDto((BoardEntity) entities[0], (MemberEntity) entities[1], (Long) entities[2]));
        Page<Object[]> result =
                boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO getById(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] en = (Object[]) result;
        return entityToDto((BoardEntity) en[0], (MemberEntity) en[1], (Long) en[2]);
    }

    @Override
    public Long modify(BoardDTO boardDTO) {
        Optional<BoardEntity> result = boardRepository.findById(boardDTO.getBno());
        BoardEntity boardEntity = null;
        if (result.isPresent()) {
            boardEntity = (BoardEntity) result.get();
            boardEntity.changeTitle(boardDTO.getTitle());
            boardEntity.changeContent(boardDTO.getContent());
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
