package com.example.springsecuritystudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// 커스텀 인증과 인가를 위한 구성파일
@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화
public class SecurityConfig {

    // 필터 빈 생성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // HttpSecurity 빈에 아래 설정 추가
        http
                .authorizeHttpRequests(auth -> auth // 람다 식으로 작성 해야함
                        // 루트 디렉토리, login 디렉토리는 모두 허용(인증 필요x)
                        .requestMatchers("/","/login").permitAll()
                        // admin 페이지는 인증된 사용자가 ADMIN 권한을 갖고 있어야함(인가)
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // my경로 이후의 모든 경로에 대해 ADMIN 혹은 USER권한 보유시 접근 가능
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                        // 위에서 설정하지 않은 모든 요청은 인증(Authenticated)을 거쳐야 접근 가능(로그인 필요)
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
