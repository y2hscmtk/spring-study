package com.example.new_jwt_practice.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

// 폼 로그인 방식의 경우 스프링 시큐리티 필터들 중 UsernamePasswordAuthenticationFilter에 의해 수행된다.
// 해당 필터는 AuthenticationManager를 사용하여 사용자의 인증절차를 수행하는데
// 이때 UserDetailsService가 제공하는 UserDetails를 사용한다.
// 해당 프로젝트의 경우 폼 로그인 방식을 disable하였으므로
// 사용자의 로그인 요청을 수행하기 위한 UsernamePasswordAuthenticationFilter를 커스텀하여 로그인 요청을 수행한다.
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager; // 인증 절차를 수행할 때 사용

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        // 클라이언트 요청에서 username, passowrd 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 토큰에 담아야한다.
        // username, password, authorities
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(username, password,null);

        // 토큰 정보를 바탕으로 검증 수행 및 결과 반환
        // 검증은 AuthenticationManager가 UserDetails를 바탕으로 수행한다.
        // DB의 정보를 바탕으로 UserDetailsService에 의해 UserDetails가 생성되면 AuthenticationManager가 이를 토대로 검증한다.
        Authentication authentication = authenticationManager.authenticate(authToken);

        return authentication;
    }

    // 로그인 성공시 실행
    // 로그인 성공시 사용자 정보를 담고 있는 JWT 토큰을 발급 해준다.
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("success!");
    }

    // 로그인 실패시 실행

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        System.out.println("fail!");
    }
}
