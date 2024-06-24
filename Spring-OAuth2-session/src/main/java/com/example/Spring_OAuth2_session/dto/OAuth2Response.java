package com.example.Spring_OAuth2_session.dto;

// OAuth2 인증 서버로부터 전달 받을 정보
public interface OAuth2Response {
    // 제공자 정보를 얻어오기 위함
    String getProvider();

    String getProviderId();

    // 사용자 정보를 얻어오기 위함
    String getEmail();

    String getName();
}
