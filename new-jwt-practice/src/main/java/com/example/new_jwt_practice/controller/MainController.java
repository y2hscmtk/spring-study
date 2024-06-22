package com.example.new_jwt_practice.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RestController
public class MainController {

    @Data
    @Builder
    public static class MemberInfo {
        private String username; // 사용자 이름
        private String role; // 권한
    }


    @GetMapping("/")
    public MemberInfo mainP() {
        // JWT는 STATELESS한 방식으로 구동되긴 하지만 일시적으로 세션을 만들어 사용한다.
        // 따라서 세션을 통해 JWT에 내포된 사용자 정보를 추출할 수 있다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        // 권한 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String authority = auth.getAuthority();

        MemberInfo memberInfo = MemberInfo.builder()
                .username(name)
                .role(authority)
                .build();

        return memberInfo;
    }
}
