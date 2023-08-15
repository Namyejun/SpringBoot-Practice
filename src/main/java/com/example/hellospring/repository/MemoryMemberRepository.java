package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>(); // 실무에서는 동시성 문제가 있을 수 있음 따라서 ConcurrentHashMap을 써야함
    private static long sequence = 0L; // 얘도 동시성 문제로 인해서 원래 AtomicLong 이건가를 써야함

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // ofNullable로 감싸서 반환해줌
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name) )
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // test에서 나는 에러를 해결하기 위해 만든 메소드
    public void clearStore() {
        store.clear();
    }

}
// 동작 확인을 할 때 테스트 케이스를 작성함