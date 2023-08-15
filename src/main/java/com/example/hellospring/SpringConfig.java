package com.example.hellospring;

import com.example.hellospring.aop.TimeTraceAop;
import com.example.hellospring.repository.*;
import com.example.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration // 가시적으로 설정파일이야 ~ , Bean 등록할꺼야 ~ 라는 것, 싱글톤으로 만들기 위함
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired // 생성자 하나라서 생략해도 됨
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } // 이렇게 만들어놓으면 Spring Data Jpa가 구현체를 만들어 놓은게 등록이 된다고 함

    @Bean // @Bean만 사용해도 스프링 빈으로 등록은 되지만 싱글톤이 유지되지는 않는다.
    public MemberService memberService() {
        return new MemberService(memberRepository); // memberRepository 주입
    }

//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }
}