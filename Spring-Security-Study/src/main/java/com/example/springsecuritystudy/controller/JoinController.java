package com.example.springsecuritystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    // 회원가입 페이지로 이동
    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    // 회원가입
    @PostMapping("/joinProc")
    public String joinProcess() {
        return "redirect:/login"; // 로그인 페이지로 리다이렉트
    }
}
