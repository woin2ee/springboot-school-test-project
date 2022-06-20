package iducs.springboot.yjwboard.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import iducs.springboot.yjwboard.domain.Member;
import iducs.springboot.yjwboard.domain.PageRequestDTO;
import iducs.springboot.yjwboard.domain.PageResultDTO;
import iducs.springboot.yjwboard.entity.MemberEntity;
import iducs.springboot.yjwboard.entity.QMemberEntity;
import iducs.springboot.yjwboard.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) { // 생성자 주입
        this.memberRepository = memberRepository;
    }

    private MemberEntity dtoToEntity(Member member) {
        return MemberEntity.builder()
                .seq(member.getSeq())
                .id(member.getId())
                .pw(member.getPw())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .address(member.getAddress())
                .level(member.getLevel())
                .build();
    }

    private Member entityToDto(MemberEntity entity) {
        return Member.builder()
                .seq(entity.getSeq())
                .id(entity.getId())
                .pw(entity.getPw())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .level(entity.getLevel())
                .build();
    }

    @Override
    public void create(Member member) {
        MemberEntity entity = dtoToEntity(member);
        memberRepository.save(entity);
    }

    @Override
    public Member readById(Long seq) {
        Member member = null;

        Optional<MemberEntity> entity = memberRepository.findById(seq);
        if(entity.isPresent()) {
            member = entityToDto(entity.get());
        }

        return member;
    }

    @Override
    public List<Member> readAll() {
        List<Member> members = new ArrayList<>();
        List<MemberEntity> entities = memberRepository.findAll();
        for (MemberEntity entity : entities) {
            members.add(entityToDto(entity));
        }
        return members;
    }

    @Override
    public void update(Member member) {
        MemberEntity entity = dtoToEntity(member);
        memberRepository.save(entity);
    }

    @Override
    public void delete(Member member) {
        MemberEntity entity = dtoToEntity(member);
        memberRepository.deleteById(entity.getSeq());
    }

    @Override
    public Member readByName(Member member) {
        return null;
    }

    @Override
    public Member readByEmail(String email) {
        return null;
    }

    @Override
    public PageResultDTO<Member, MemberEntity> readListBy(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("seq").ascending());

        BooleanBuilder booleanBuilder = findByCondition(pageRequestDTO);
        Page<MemberEntity> result = memberRepository.findAll(booleanBuilder, pageable);

        Function<MemberEntity, Member> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public Member loginByEmail(Member member) {
        Member result = null;
        Object entity = memberRepository.getMemberByEmail(member.getEmail(), member.getPw());
        if (entity != null) {
            result = entityToDto((MemberEntity) entity);
        }
        return result;
    }

    private BooleanBuilder findByCondition(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getType();
        String level = pageRequestDTO.getLevel();
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QMemberEntity qMemberEntity = QMemberEntity.memberEntity;

        BooleanExpression expression = qMemberEntity.seq.gt(0L); // where seq > 0 and title == "ti"
        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0 && level == null) {
            return booleanBuilder;
        }



        String keyword = pageRequestDTO.getKeyword();

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("e"))
            conditionBuilder.or(qMemberEntity.email.contains(keyword));
        if(type.contains("p"))
            conditionBuilder.or(qMemberEntity.phone.contains(keyword));
        if(type.contains("a"))
            conditionBuilder.or(qMemberEntity.address.contains(keyword));

        if(level != null) {
            conditionBuilder.and(qMemberEntity.level.contains(level));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
