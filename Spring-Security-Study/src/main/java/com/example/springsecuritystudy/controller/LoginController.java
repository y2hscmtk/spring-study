package com.example.springsecuritystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // /login 요청시 로그인 페이지로 연결
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
