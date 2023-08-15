package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em; // jpa는 entity manager로 모든 게 동작한다.

    @Autowired
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
    // data-jpa 라이브러리를 받으면 부트가 entity manager라는 걸 생성해줌
    // 우리는 그 만들어진 것은 injection 받으면 됨
    // 얘는 database connection 정보 data source 등 이것 저것 가지고 만들어진 애임

    @Override
    public Member save(Member member) {
        em.persist(member); // 영속하다 뜻으로 저장과 비슷
        return member; // return이 없어서 그대로 리턴, member에다가 setId 까지 해준다고 함 ㄷㄷ
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
//        return Optional.ofNullable(em.createQuery("select m from Member m where m.name = :name", Member.class)
//                .setParameter("name", name)
//                .getSingleResult());

        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny(); // 강사님은 이렇게 했음
    }
    // pk가 파라미터로 들어가지 않는 쿼리는 jpql을 작성해줘야한다.
    // jpql이라는 쿼리 언어로 객체 대상으로 쿼리를 날림
    // select m from Member as m 으로 member 엔티티 자체를 select 함
    // 다음 시간에 배울 jpa를 스프링에서 한번 감싸서 제공하는 Spring data jpa를 사용하면 jpql도 안써도 된다.
    // jpa를 사용하려면 항상 트랜잭션이 수반돼야함

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); // ctrl + alt + n -> inline 이라고 단축해줌

    }
}
