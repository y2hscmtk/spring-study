package com.example.oauth2_jwt_api.dto;

// OAuth2 응답 데이터 공통 응답 처리
public interface OAuth2Response {
    // 제공자
    String getProvider();
    // 제공자에서 발급해주는 아이디(세션)
    String getProviderId();
    // 이메일
    String getEmail();
    // 사용자 실명 (설정한 이름)
    String getName();
}
