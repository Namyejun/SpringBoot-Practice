package com.example.hellospring.controller;

import com.example.hellospring.domain.Member;
import com.example.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// 컨트롤러는 서비스에 의존한다. 컨트롤러가 서비스를 통해서 가입, 조회등을 하기 때문에
@Controller
// 스프링 컨테이너라는 통이 생기고 거기에 컨트롤러 어노테이션이 있으면 멤버컨트롤러 객체를 생성해 넣어두고 스프링이 관리한다.
// 스프링 컨테이너에서 스프링 빈이 관리된다고 함.
public class MemberController {

    // private final MemberService memberService = new MemberService();
    // new로 생성해서 쓸 수도 있지만 spring이 관리를 하면 컨테이너에 등록을 하고 컨테이너에서 받아서 쓰도록 해야함
    // 스프링 컨테이너에 등록을 하면 딱 하나만 등록이 되고 여러 효과를 볼 수 있다.

    private final MemberService memberService;

    @Autowired // 멤버 컨트롤러를 스프링 컨테이너가 뜰 때 생성하는데 생성자를 호출하게 되고 autowired가 달려있으면 서비스를 컨테이너에서 가져와서 연결을 시켜줌
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService : " + memberService.getClass());
    }
    // 그냥 이렇게 쓰면 a bean of type 'com.example.hellospring.service.MemberService' that could not be found. 이 에러남

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm memberForm) {
        Member member = new Member();
        member.setName(memberForm.getName());

        System.out.println("name : " + member.getName());

        memberService.join(member);

        return "redirect:/"; // 회원가입 후 리다이렉트
    }

    @GetMapping("/members")
    public String list(Model model) { // 데이터를 동적으로 담기 위한 모델
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members); // html 파일에 동적으로 데이터를 쏴줌
        return "/members/memberList";
    }
}
