package com.example.springsecuritystudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// SecurityFilterChain 에 의해 /admin 경로로 접근시 인증/인가 절차를 수행하는지 확인한다.
@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
