package com.example.springsecuritystudy.controller;

import com.example.springsecuritystudy.dto.JoinDTO;
import com.example.springsecuritystudy.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    // 회원가입 페이지로 이동
    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    // 회원가입
    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO); // 회원가입
        return "redirect:/login"; // 로그인 페이지로 리다이렉트
    }
}
