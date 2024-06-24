package com.example.Spring_OAuth2_session.service;

import com.example.Spring_OAuth2_session.dto.CustomOAuth2User;
import com.example.Spring_OAuth2_session.dto.GoogleResponse;
import com.example.Spring_OAuth2_session.dto.NaverResponse;
import com.example.Spring_OAuth2_session.dto.OAuth2Response;
import com.example.Spring_OAuth2_session.entity.Member;
import com.example.Spring_OAuth2_session.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

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


        // 엔티티에서 회원 이름으로 사용하기 위함
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        Optional<Member> findMember = memberRepository.findByUsername(username);
        String role = null; // 임시로 null 선언 => CustomOAuth2User에서 사용하기 위함
        // 1. 존재하지 않는 회원인 경우
        if (findMember.isEmpty()) {
            // 새로운 회원으로 생성
            Member member = Member.builder()
                    .username(username)
                    .role("ROLE_USER")
                    .email(oAuth2Response.getEmail())
                    .build();
            role = "ROLE_USER";
            memberRepository.save(member);
        } else {
            // 2. 존재하는 회원인 경우
            // => 한번 가입했던 회원의 정보가 업데이트 되었을 수도 있음 => 업데이트
            Member member = findMember.get();
            role = member.getRole();
            member.changeEmail(oAuth2Response.getEmail()); // 상태 변경
            memberRepository.save(member); // 더티체킹으로 인한 상태 업데이트
        }
        // Spring Security 의 UserDetails에 해당
        return new CustomOAuth2User(oAuth2Response,role);
    }
}
