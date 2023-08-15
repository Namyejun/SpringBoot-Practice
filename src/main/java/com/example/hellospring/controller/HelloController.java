package com.example.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody // http의 body부에 데이터를 직접 넣겠다라는 뜻
    public String helloString(@RequestParam("name") String name){
        return "hello " + name; // 뷰 없이 이 데이터가 그대로 감. 소스보기를 하면 html 태그가 없음
    }

    // 문자가 아니라 데이터를 내놓으라는 상황때문에 API를 씀
    @GetMapping("hello-api")
    @ResponseBody // json이 기본
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name); // cmd + shft + enter = 자동완성
        return hello; // json data가 내려감, 과거에는 xml이었는데
    }
    // 자바빈 규약, property 접근 방식
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
