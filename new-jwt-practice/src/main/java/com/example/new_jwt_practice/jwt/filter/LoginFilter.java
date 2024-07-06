package com.example.new_jwt_practice.jwt.filter;

import com.example.new_jwt_practice.jwt.dto.CustomUserDetails;
import com.example.new_jwt_practice.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

// 폼 로그인 방식의 경우 스프링 시큐리티 필터들 중 UsernamePasswordAuthenticationFilter에 의해 수행된다.
// 해당 필터는 AuthenticationManager를 사용하여 사용자의 인증절차를 수행하는데
// 이때 UserDetailsService가 제공하는 UserDetails를 사용한다.
// 해당 프로젝트의 경우 폼 로그인 방식을 disable하였으므로
// 사용자의 로그인 요청을 수행하기 위한 UsernamePasswordAuthenticationFilter를 커스텀하여 로그인 요청을 수행한다.
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager; // 인증 절차를 수행할 때 사용

    // 사용자 로그인 성공시 JWT 토큰을 발급해주기 위해 JwtUtil을 주입받는다.
    private final JwtUtil jwtUtil;

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

        // 로그인 성공시, 사용자에게 토큰 발급
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        // 사용자 이름 추출
        String username = customUserDetails.getUsername();
        // 사용자 권한 추출
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 만료시간 10시간으로 설정
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10 * 1000L);

        // HTTP 반환 헤더에 "Authorization" 키 값으로 토큰 반환
        response.addHeader("Authorization","Bearer " + token);

    }

    // 로그인 실패시 실행
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        // 실패 코드 반환
        response.setStatus(401);
    }
}
