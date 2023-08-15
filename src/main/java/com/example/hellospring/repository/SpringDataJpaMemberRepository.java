package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // 인터페이스는 다중 상속이 된다.
    // JpaRepository는 도메인과 그에 해당하는 키 값의 타입을 받도록 한다. 약간 제네릭
    // 인터페이스만 있지만 spring data jpa가 JpaRepository를 받고 있으면 구현체를 자동으로 만들고 bean으로 등록해줌
    @Override
    Optional<Member> findByName(String name); // 이러고 끝난다고 함 ㄷㄷ
}
