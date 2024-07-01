package com.example.Spring_OAuth2_session.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class CustomOAuth2AuthorizedClientService {

    // 소셜 네트워크 API 정보를 인메모리 방식이 아닌 JDBC를 사용하여 관리
    // 사용자가 로그인하게 되면
    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(JdbcTemplate jdbcTemplate, ClientRegistrationRepository clientRegistrationRepository) {

        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate ,clientRegistrationRepository);
    }
}
