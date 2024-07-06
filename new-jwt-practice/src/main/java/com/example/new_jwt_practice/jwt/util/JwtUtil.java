package com.example.new_jwt_practice.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰을 통해 사용자 이름 추출
    public String getUsername(String token) {
        String username = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
        System.out.println("Extracted username: " + username);
        return username;
    }

    // 토큰으로부터 사용자 권한 추출
    public String getRole(String token) {
        String role = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
        System.out.println("Extracted role: " + role);
        return role;
    }

    // 토큰이 만료되었는지 확인, 오늘 날짜와 토큰에 저장된 만료일자를 비교한다.
    // 만료되었다면 true
    public Boolean isExpired(String token) {
        try {
            Date expiration = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            System.out.println("Token expiration time: " + expiration);
            boolean isExpired = expiration.before(new Date());
            System.out.println("Is token expired? " + isExpired);
            return isExpired;
        } catch (Exception e) {
            System.out.println("Token expired or invalid: " + e.getMessage());
            return true;
        }
    }

    // 토큰 생성 메소드
    // 사용자 이름, 권한, 토큰 유지 시간을 매개변수로 전달받는다.
    public String createJwt(String username, String role, Long expiredMs) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + expiredMs);
        System.out.println("Token creation time: " + issuedAt);
        System.out.println("Token expiration time: " + expiration);

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
