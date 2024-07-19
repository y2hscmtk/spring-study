package com.example.oauth2_jwt_api.service;

import com.example.oauth2_jwt_api.dto.GoogleResponse;
import com.example.oauth2_jwt_api.dto.NaverResponse;
import com.example.oauth2_jwt_api.dto.OAuth2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    // userRequest : OAuth2로부터 제공 받는 사용자 정보
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 사용자 정보 DTO 로드
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User = " + oAuth2User);

        // 어떤 서비스를 통해 얻어오는 정보인지 확인해야함
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        // 네이버와 구글에서 제공해주는 데이터 형식이 다르기 때문에 희망하는 서비스에 맞춰서 dto를 구현해야함
        if (registrationId.equals("naver")) {
            // naver { resultcode = 00, message=success,response={id=12132,name="개발자"}
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            // google { resultcode = 00, message = success, id = 12312, name = "개발자" }
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else { // 다른 소셜 로그인의 경우
            return null;
        }

        return super.loadUser(userRequest);
    }
}
