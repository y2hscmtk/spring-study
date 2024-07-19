package com.example.oauth2_jwt_api.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

// 구글의 경우 아래와 같은 응답을 갖는다.
// google { resultcode = 00, message = success, id = 12312, name = "개발자" }
@RequiredArgsConstructor
public class GoogleResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
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
