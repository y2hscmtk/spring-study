package com.jwt_practice.util.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// 클라이언트에 보낼 토큰을 위한 DTO
@Builder
@Data
@AllArgsConstructor
public class JwtTokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
