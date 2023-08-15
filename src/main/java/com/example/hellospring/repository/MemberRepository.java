package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

// DB와 소통 창구이기 때문에 CRUD를 수행할 수 있는 방법이 필요하다.
public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id); // java 8에 들어간 기능으로 null일 수도 아닐 수도 있기 때문에 그냥 반환하는 거보다 좀 안정적
    Optional<Member> findByName(String name);
    List<Member> findAll();

}