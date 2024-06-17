package com.example.new_jwt_practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 시큐리티를 위한 구성파일임을 알림
public class SecurityConfig {

    // 사용자 비밀번호를 관리하기 위한 암호화 클래스
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // csrf disable
        http
                .csrf(auth -> auth.disable()); // JWT 방식은 세션을 Stateless로 관리하기 때문에 csrf 공격에 비교적 안전하다.

        // JWT 방식을 사용할 것이므로 이외의 방식은 모두 비활성화 한다.
        // Form 로그인 방식 disable
        http
                .formLogin(auth -> auth.disable());

        // http basic 인증 방식 disable
        http
                .httpBasic(auth -> auth.disable());

        // 경로별 인가 설정
        http
                .authorizeHttpRequests(auth -> auth
                        // login, root, join 경로의 요청에 대해서는 모두 허용
                        .requestMatchers("/login", "/", "/join").permitAll()
                        // admin 경로 요청에 대해서는 ADMIN 권한을 가진 경우에만 허용
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // 이외의 요청에 대해서는 인증된 사용자만 허용
                        .anyRequest().authenticated()
                );

        // 가장 중요
        // JWT 방식에서는 세션을 Stateless 상태로 관리하게 됨(서버 세션에 저장하지 않음)
        // 따라서 세션을 STATELESS 상태로 설정 해주어야 한다.
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
