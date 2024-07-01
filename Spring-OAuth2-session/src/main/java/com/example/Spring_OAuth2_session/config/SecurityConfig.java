package com.example.Spring_OAuth2_session.config;

import com.example.Spring_OAuth2_session.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    // 시큐리티 필터 활성화
    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf, 폼 로그인 방식, 베이직 로그인 방식 비활성화
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(login -> login.disable())
                .httpBasic(basic -> basic.disable());

        // OAuth2 설정
        // 커스텀 서비스 등록
        http
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // oauth2 커스텀 로그인 페이지 등록
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)));

        // "/", "/oauth2/**", "/login/**" 경로에 대해서는 모두 허용
        // 그 외의 요청에 대해서는 인증된 사용자에 한해서 허용
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
