package com.example.new_jwt_practice.jwt.filter;

import com.example.new_jwt_practice.entity.Member;
import com.example.new_jwt_practice.jwt.dto.CustomUserDetails;
import com.example.new_jwt_practice.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 사용자가 JWT를 첨부하여 요청시, JWT를 바탕으로 사용자 정보를 확인하여 인가처리를 수행한다.
// 이때 JWT를 검증(올바르게 생성된 토큰인지, 올바른 권한을 보유중인지, 등)하고, 한번만 사용할 임시 세션을 만들어 요청을 수행한다.
// STATELESS하게 관리되므로 한번의 요청이후 해당 토큰은 사라진다.
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    // JWT 검증용
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // 토큰 추출
        String authorization = request.getHeader("Authorization");
        // 토큰 유효성 검증
        // - Bearer 접두사가 있는지, 토큰이 실제로 존재하는지, 등
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            filterChain.doFilter(request, response);
            return;
        }
        // Bearer 접두사 제거
        String token = authorization.split(" ")[1];

        // 토큰 소멸시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request,response);
            return;
        }

        // 토큰에서 username, role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // 사용자 객체 생성 및 값 주입
        Member member = Member.builder()
                .username(username)
                .role(role)
                .build();

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        // 스프링 시큐리티 인증 토큰 생성 => 해당 토큰을 바탕으로 AuthenticationManager에서 사용자 권한 검증 수행
        Authentication authToken
                = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request,response);
    }
}
