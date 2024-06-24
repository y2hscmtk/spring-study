package com.example.Spring_OAuth2_session.dto;

import java.util.Map;

public class GoogleResponse implements OAuth2Response{
    private final Map<String, Object> attribute;

    // 구글의 경우 형식이 동일하기 때문에 별도로 꺼낼 필요없음
    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
