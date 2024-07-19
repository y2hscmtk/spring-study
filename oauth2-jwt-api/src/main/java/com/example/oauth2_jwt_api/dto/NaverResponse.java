package com.example.oauth2_jwt_api.dto;

import java.util.Map;
public class NaverResponse implements OAuth2Response{

    // 네이버로부터 얻은 정보를 저장하기 위함
    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {
        // 네이버의 경우 필요한 정보가 response 키 하위에 존재함
        // naver { resultcode = 00, message=success,response={id=12132,name="개발자"}
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
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
