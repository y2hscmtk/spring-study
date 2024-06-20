package com.example.new_jwt_practice.jwt.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// JWTUtil 0.12.3
// JWT를 검증할 메소드, 생성할 메소드를 구현
// username, role, 생성일, 만료일 확인용 메소드
// => 생성 : 토큰의 Payload에 username, role, 생성일, 만료일을 저장
@Component
public class JwtUtil {
    private SecretKey secretKey;

    // application.properties에 저장된 시크릿키를 대상으로 객체 키 생성
    public JwtUtil(@Value("${spring.jpa.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // 토큰을 통해 사용자 이름 추출
    public String getUsername(String token) {

        return Jwts.parser()
                .verifyWith(secretKey) // 시크릿 키를 사용하여 복호화한다.
                .build() // 복호화에 사용할 Jwts Parser 생성
                // 생성된 Parser를 사용하여 token으로 부터 정보 추출
                .parseSignedClaims(token)
                .getPayload().get("username", String.class);
    }

    // 토큰으로부터 사용자 권한 추출
    public String getRole(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    // 토큰이 만료되었는지 확인, 오늘 날짜와 토큰에 저장된 만료일자를 비교한다.
    // 만료되었다면 true
    public Boolean isExpired(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    // 토큰 생성 메소드
    // 사용자 이름, 권한, 토큰 유지 시간을 매개변수로 전달받는다.
    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }


}
