package com.example.Spring_OAuth2_session.service;

import com.example.Spring_OAuth2_session.dto.CustomOAuth2User;
import com.example.Spring_OAuth2_session.dto.GoogleResponse;
import com.example.Spring_OAuth2_session.dto.NaverResponse;
import com.example.Spring_OAuth2_session.dto.OAuth2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 사용자 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());

        // 인증을 제공받을 때, 대상이 어떤 곳인지(네이버,구글,등) 확인해야함
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        // 각 서비스가 제공하는 방식이 다르므로 별도로 구현해주어야 한다.
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // Spring Security 의 UserDetails에 해당

        // 기본 권한이 있다고 가정
        String role = "ROLE_USER";

        return new CustomOAuth2User(oAuth2Response,role);
    }
}
