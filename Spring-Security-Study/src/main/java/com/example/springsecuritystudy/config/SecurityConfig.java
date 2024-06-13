package com.example.springsecuritystudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 커스텀 인증과 인가를 위한 구성파일
@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화
public class SecurityConfig {

    // 비밀번호 암호화를 위한 빈 생성
    @Bean // 빈으로 등록시킴으로서 어디서든 사용할 수 있게된다.
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // 필터 빈 생성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // HttpSecurity 빈에 아래 설정 추가
        http
                .authorizeHttpRequests(auth -> auth // 람다 식으로 작성 해야함
                        // 루트 디렉토리, login 디렉토리는 모두 허용(인증 필요x)
                        .requestMatchers("/", "/login","/join").permitAll()
                        // admin 페이지는 인증된 사용자가 ADMIN 권한을 갖고 있어야함(인가)
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // my경로 이후의 모든 경로에 대해 ADMIN 혹은 USER권한 보유시 접근 가능
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        // 위에서 설정하지 않은 모든 요청은 인증(Authenticated)을 거쳐야 접근 가능(로그인 필요)
                        .anyRequest().authenticated()
                )
                // 로그인 설정 추가
                .formLogin(auth -> auth
                        .loginPage("/login") // 로그인 페이지로 리다이렉트
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                )
                // 로그인시 POST 사용을 위해 csrf 설정 해제
                // csrf 설정은 디폴트로 작동된다. => csrf가 동작되면 POST 요청을 보낼때 csrf 토큰도 보내야 요청이 수행된다.
                // 하지만 개발환경에서는 토큰을 따로 보내주도록 설정하지 않았기 때문에 임시로 비 활성화 한다.
                .csrf(auth -> auth.disable());

        return http.build();
    }
}
