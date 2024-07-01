package com.example.Spring_OAuth2_session.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

// OAuth2 구성 정보를 Client Registration을 사용하여 관리
// 각 소셜 네트워크에 대한 설정 정보를 application.properties가 아닌 컴포넌트를 사용하여 인메모리에 등록하여 사용할 수 있다.
// 일반적으로 application.properties가 아닌 이와 같은 방식으로 관리한다.
@Component
public class SocialClientRegistration {

    @Value("${oauth2.naver.id}")
    private String naverId;

    @Value("${oauth2.naver.secret}")
    private String naverSecret;

    @Value("${oauth2.google.id}")
    private String googleId;

    @Value("${oauth2.google.secret}")
    private String googleSecret;

    public ClientRegistration naverClientRegistration() {

        // 소셜 네트워크 API 로부터 발급받은 id과 secret으로 대체하여 작성한다
        return ClientRegistration.withRegistrationId("naver")
                .clientId(naverId)
                .clientSecret(naverSecret)
                .redirectUri("http://localhost:8080/login/oauth2/code/naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("name", "email")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }

    public ClientRegistration googleClientRegistration() {

        return ClientRegistration.withRegistrationId("google")
                .clientId(googleId)
                .clientSecret(googleSecret)
                .redirectUri("http://localhost:8080/login/oauth2/code/google")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .issuerUri("https://accounts.google.com")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .build();
    }
}
