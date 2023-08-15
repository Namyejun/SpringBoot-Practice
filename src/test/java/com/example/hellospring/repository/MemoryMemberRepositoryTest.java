package com.example.hellospring.repository;


import com.example.hellospring.domain.Member;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 메소드가 끝날 때마다 실행되는 콜백 메소드
    public void afterEach() {
        repository.clearStore(); // findByName 여기서 오류나는 문제 해결하기 위해 만들어짐
    }

    @Test // 실행이 가능해짐
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // 반환 타입 optional 에서 값을 꺼내는 경우에 get으로 꺼낸다. test코드의 경우에 다른 경우에는 get으로 바로 꺼내면 안됨
        Member result = repository.findById(member.getId()).get();

        // System.out.println("Result : " + (result == member)); // 터미널을 계속 볼 수는 없어서 안씀
        // assertEquals(member, result); // (기대값, 실행값), 만약에 다른 값이 들어오면 tests faild 가 뜬다. 같으면 tests passed
        assertThat(result).isEqualTo(member); // (실행값, 기대값)요새는 이걸 많이 씀, 조금 더 파라미터가 명확하다고 함
        // 실무에서는 빌드 툴하고 묶어서 빌드할 때 테스트에 통과 못하면 다음단계로 못 넘어가게 막음
    }

    @Test
    public void findByName(){
        // 3개 다같이 돌리면 이게 에러남
        // findall -> findbyname -> save 순으로 테스트 실행됨, name이 같은게 두 개가 나옴 따라서 객체가 다른게 나옴
        // 이 순서는 보장이 되지 않음
        // 순서에 의존되게 설계하면 안됨.
        Member member1 = new Member();
        member1.setName("name1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("name2");
        repository.save(member2);

        Member result = repository.findByName("name1").get(); // 왜 get없애고 optional로 받으니 에러나지?
        assertThat(result).isEqualTo(member1);

        // Optional<Member> result = repository.findByName("name1");
        // optional로 한번 감싸져서 나옴 Optional[com.example.hellospring.domain.Member@2925bf5b] 이렇게
        // assertThat(result.get()).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("name1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("name2");
        repository.save(member2);

        List<Member> memberList = repository.findAll();

        ArrayList<Member> expect = new ArrayList<>();
        expect.add(member1);
        expect.add(member2);

        assertThat(memberList).isEqualTo(expect);
    }
}
