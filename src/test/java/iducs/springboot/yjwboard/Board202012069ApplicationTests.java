package iducs.springboot.yjwboard;

import iducs.springboot.yjwboard.entity.MemberEntity;
import iducs.springboot.yjwboard.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
class Board202012069ApplicationTests {

    @Autowired
    MemberRepository memberRepository;

//	@Test // Unit Test: JUnit 도구 활용 -> 통합 테스트(Integration Test)
	void contextLoads() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			MemberEntity member = MemberEntity.builder().id("id " + i).pw("pw " + i).name("name " + i).build();
			memberRepository.save(member);
		});
	}

    @Test
    void testMemberInitialize() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            MemberEntity memberEntity = MemberEntity.builder()
                    .id("id " + i)
                    .pw("pw " + i)
                    .name("name " + i)
                    .email("email" + i + "@induk.ac.kr")
                    .phone("phone " + new Random().nextInt(50))
                    .address("address " + new Random().nextInt(50))
                    .level("" + (i % 3 + 1))
                    .build();
            memberRepository.save(memberEntity);
        });
    }
}
