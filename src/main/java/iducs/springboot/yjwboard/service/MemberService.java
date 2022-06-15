package iducs.springboot.yjwboard.service;

import iducs.springboot.yjwboard.domain.Member;
import iducs.springboot.yjwboard.domain.PageRequestDTO;
import iducs.springboot.yjwboard.domain.PageResultDTO;
import iducs.springboot.yjwboard.entity.MemberEntity;

import java.util.List;

public interface MemberService {
    void create(Member member);
    Member readById(Long seq);
    List<Member> readAll();
    void update(Member member);
    void delete(Member member);

    Member readByName(Member member);
    Member readByEmail(String email);

    PageResultDTO<Member, MemberEntity> readListBy(PageRequestDTO pageRequestDTO);
}
