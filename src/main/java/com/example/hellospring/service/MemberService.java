package com.example.hellospring.service;

import com.example.hellospring.domain.Member;
import com.example.hellospring.repository.MemberRepository;
import com.example.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional // 회원가입 할 때 트랜잭션이 필요하므로, 같은 이름 중복 확인도 트랜잭션 내에서 확인돼야함
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) { // dependency injection
        this.memberRepository = memberRepository;
    }

    /*
    * 회원 가입
    * */
    public Long join(Member member){
        validateDuplicateMember(member); // 이렇게 로직이 쫙 나오는 경우에는 메소드로 뽑는게 좋음 shift + ctrl + alt + t
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) { // 리포지토리는 단순히 저장소에 넣다 뺐다하는 느낌, 서비스는 조금 더 비즈니스에 가까움, 용어도 비즈니스적으로 써야함
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /*
     * 전체 회원 조회
     * */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /*
    * 아이디 변수로 조회
    * */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
