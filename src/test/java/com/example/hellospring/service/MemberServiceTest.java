package com.example.hellospring.service;

import com.example.hellospring.domain.Member;
import com.example.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        // 이렇게 해주는 이유가 리포지토리가 static이라서 전체가 동시에 갖기 때문에 막 섞일 수 있음
        // 그래서 하나로 씀
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach // 메소드가 끝날 때마다 실행되는 콜백 메소드
    public void afterEach() {
        memberRepository.clearStore(); // findByName 여기서 오류나는 문제 해결하기 위해 만들어짐
    }

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