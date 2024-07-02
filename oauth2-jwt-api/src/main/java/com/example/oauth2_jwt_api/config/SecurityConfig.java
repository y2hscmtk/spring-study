package com.example.oauth2_jwt_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // api 서버이므로 csrf 공격에 비교적 안전하다.
        http
                .csrf(auth -> auth.disable());
        // api 서버이므로 별도로 설정하지 않는다.
        http
                .formLogin(auth -> auth.disable())
                .httpBasic(auth -> auth.disable());

        // oauth2
        http
                .oauth2Login(Customizer.withDefaults());

        // 경로별 인가작업
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated());

        // 세션 설정 => STATELESS
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
