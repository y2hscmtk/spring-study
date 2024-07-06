package com.example.new_jwt_practice.controller;

import com.example.new_jwt_practice.dto.MemberInfo;
import com.example.new_jwt_practice.jwt.dto.CustomUserDetails;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RestController
public class MainController {

    @GetMapping("/main1")
    public ResponseEntity<?> mainP() {
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

        return ResponseEntity.ok(memberInfo);
    }

    // Controller단에서 사용자 정보를 추출하는 2번째 방법
    @GetMapping("/main2")
    public ResponseEntity<?> getUserInfoByUserDetails(
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        MemberInfo memberInfo =
                MemberInfo.builder().username(username).role(role).build();

        return ResponseEntity.ok(memberInfo);
    }

    // CustomUserDetails에 작성해둔 getMemberInfo()를 사용하여 중복을 줄일 수 있다.
    @GetMapping("/main3")
    public ResponseEntity<?> getUserInfoByCustomUserDetails(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        MemberInfo memberInfo = userDetails.getMemberInfo();
        return ResponseEntity.ok(memberInfo);
    }

}
