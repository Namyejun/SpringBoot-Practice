package com.example.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 요청이 오면 먼저 컨트롤러 먼저 뒤져본다
    // 만약 컨트롤러가 없으면 static을 뒤지고
    // 있으면 templates를 뒤진다. -> 있어서 static의 index.html이 무시됨
    @GetMapping("/") // http://localhost:8080/
    public String home() {
        return "home";
    }
}
