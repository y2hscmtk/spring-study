package com.example.springjwthard.jwt;

import com.example.springjwthard.entity.RefreshEntity;
import com.example.springjwthard.repository.RefreshEntityRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final Long REFRESH_EXPIRED = 86400000L;
    private final Long ACCESS_EXPIRED = 600000L;

    private final AuthenticationManager authenticationManager;
    private final RefreshEntityRepository refreshEntityRepository;
    private final JWTUtil jwtUtil;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println(username);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    /**
     * 로그인 성공시 동작
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        // 현재 세션 사용자 정보
        String username = authentication.getName();
        // 현재 세션 사용자 권한 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        // access : 10분
        // refresh : 24시간 부여
        String access = jwtUtil.createJwt("access", username, role, ACCESS_EXPIRED);
        String refresh = jwtUtil.createJwt("refresh", username, role, REFRESH_EXPIRED);

        addRefreshEntity(username,refresh,REFRESH_EXPIRED);

        // 응답 설정
        // access -> Headerdd
        response.setHeader("access",access);
        // refresh -> Cookie
        response.addCookie(jwtUtil.createCookie("refresh",refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    // refresh 엔티티 데이터 저장
    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshEntity refreshEntity = RefreshEntity.builder()
                .username(username)
                .refresh(refresh)
                .expiration(date.toString())
                .build();
        refreshEntityRepository.save(refreshEntity);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }
}
