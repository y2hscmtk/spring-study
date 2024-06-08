package com.jwt_practice.security.jwt.provider;

import com.jwt_practice.security.jwt.dto.JwtToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
    // 토큰 유효기간 설정용
    private static final long ACCESS_TOKEN_VALIDITY = 86400000L; // 1 day
    private static final long REFRESH_TOKEN_VALIDITY = 2592000000L; // 30 days
    private final Key key; // Spring Security

    // application.properties에서 복호화키(secret)를 얻어와 key에 저장한다.
    // import org.springframework.beans.factory.annotation.Value; 임에 주의
    public JwtProvider(@Value("${jwt.token.key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Member 정보를 바탕으로 AccessToken과 RefreshToken을 생성한다.
    public JwtToken generateToken(Authentication authentication) {

        // 사용자의 권한정보를 얻어와 ','로 연결된 문자열로 변환한다.
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 현재 시간을 기준으로 AccessToken과 RefreshToken을 생성한다.
        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_VALIDITY); // 유효기간은 1일로 설정한다.
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // 사용자 이름을 설정한다.
                // claim : JWT 토큰 내에 포함된 정보의 단위를 의미한다.
                // 페이로드 부분에 저장되며 사용자 정보, 권한 정보 등 사용자 정의 클레임을 저장한다.
                .claim("auth", authorities) // auth 라는 이름의 클레임에 사용자 권한 정보를 설정한다.
                .setExpiration(accessTokenExpiresIn) // 만료 시간을 설정한다.
                .signWith(key, SignatureAlgorithm.HS256) // JWT 토큰에 서명을 추가한다. JWT의 무결성을 보장한다.
                .compact(); // 최종적으로 JWT 문자열을 생성한다.

        // Refresh Token 생성
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_VALIDITY);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiresIn) // 유효기간은 30일로 설정한다.
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // JWT 객체(dto.JwtToken)를 생성하고 반환한다.
        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 토큰을 복호화하여 토큰안의 정보를 얻는다.
    // JWT 토큰인 accessToken에는 다양한 정보들이 내포된다.
    public Authentication getAuthentication(String accessToken) {
        // JWT 토큰을 복호화하여 JWT 클레임을 추출(반환)한다.
        Claims claims = parseClaims(accessToken);

        // generateToken()의 Jwts.builder.claim()에 의해 저장된 auth를 확인한다.
        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 권한은 ,로 구분하여 저장하였으므로 ,를 기준으로 분할하여 리스트 형태로 얻어온다.
        Collection<? extends GrantedAuthority> authorities
                = Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 클레임에서 사용자 이름(getSubject())를 가져와 UserDetails객체를 생성한다.
        // UserDetails : Spring Security에서 사용자 정보를 표현하는 인터페이스
        // USer : UserDetails의 구현체
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 클라이언트가 제출한 JWT 토큰의 유효성을 검증한다.
    // 토큰이 유효한지, 변조되지 않았는지, 만료되지 않았는지에 대해 검사한다.
    public boolean validateToken(String token) {
        try {
            // 복호화 수행 => 복호화 과정에서 예외가 발생한다면 catch문에 걸린다.
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }


    // 토큰에서 클레임을 추출하여 반환한다.
    // 복호화에는 사전에 설정한 key를 사용한다.
    // JwtParser를 생성한뒤, parseClaimsJws를 사용하여 accessToken을 복호화한다.
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
