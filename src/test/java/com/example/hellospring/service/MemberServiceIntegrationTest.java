package com.example.hellospring.service;

import com.example.hellospring.domain.Member;
import com.example.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링에서 테스트할 때 어노테이션
@Transactional // 각 테스트에서 진행한 사항이 다음 테스트에 영향을 미치지 않도록 롤백해줌, 이거 안쓰면 레코드 누적됨
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService; // 원래는 생성자 가지고 autowired 해서 의존 주입을 썼다.
    @Autowired MemberRepository memberRepository; // 그런데 테스트는 이걸 어디 가져다 쓸게 아니라서 이렇게 해도 된다.
    // 원래는 테스트 전용 DB를 따로 구축하거나 로컬 DB가지고 테스트함
    // 이전 테스트에는 spring이 동작하지 않았는데 이제는 spring이 뜬다.
    // 테스트는 반복할 수 있어야함 -> 따라서 @Transactional 이걸 달아줘서 계속 롤백해줘야함
    @Test
    void 회원가입() { // test 코드는 과감하게 한글로 적어도 됨
        // 주석으로 제어하는 것도 괜찮음
        // given : 무엇인가가 주어진 상태에서
        Member member = new Member();
        member.setName("spring");

        // when : 실행했는데
        Long saveId = memberService.join(member);
        Member findMember = memberService.findOne(saveId).get();

        // then : 결과가 이게 나와야됨
        assertThat(member.getName()).isEqualTo(findMember.getName());
    } // 정상 플로우도 중요한데 예외 플로우가 훨씬 중요함

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        // 이 방법이 깔끔함
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // 이렇게 하기도 하는데 조금 번거로움
//        try {
//            memberService.join(member2);
//            fail("예외가 발생해야 합니다.");
//        }
//        catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        // then

    }

    @Test
    void findMembers() {


    }

    @Test
    void findOne() {
    }
}