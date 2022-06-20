package iducs.springboot.yjwboard.service;

import iducs.springboot.yjwboard.domain.BoardDTO;
import iducs.springboot.yjwboard.domain.PageRequestDTO;
import iducs.springboot.yjwboard.domain.PageResultDTO;
import iducs.springboot.yjwboard.entity.BoardEntity;
import iducs.springboot.yjwboard.entity.MemberEntity;

public interface BoardService {
    Long register(BoardDTO boardDTO);
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO getById(Long bno);
    Long modify(BoardDTO boardDTO);
    void deleteWithRepliesById(Long bno);

    void increaseViewsById(Long bno);



    default BoardEntity dtoToEntity(BoardDTO boardDTO) {
        MemberEntity memberEntity = MemberEntity.builder()
                .seq(boardDTO.getWriterSeq())
                .build();
        BoardEntity boardEntity = BoardEntity.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(memberEntity)
                .build();
        return boardEntity;
    }

    default BoardDTO entityToDto(BoardEntity entity, MemberEntity member, Long replyCount) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writerSeq(member.getSeq())
                .writerId(member.getId())
                .writerName(member.getName())
                .writerEmail(member.getEmail())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .replyCount(replyCount.intValue())
                .views(entity.getViews())
                .build();
        return boardDTO;
    }
}
