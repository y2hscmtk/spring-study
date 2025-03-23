package com.example.springjwthard.service;

import com.example.springjwthard.entity.RefreshEntity;
import com.example.springjwthard.jwt.JWTUtil;
import com.example.springjwthard.repository.RefreshEntityRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final JWTUtil jwtUtil;
    private final RefreshEntityRepository refreshEntityRepository;

    // RefreshToken -> AccessToken 재발급
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        // 요청한 Api Client 의 쿠키로부터 Refresh Token 획득
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        // refresh token 만료 여부 확인
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // DB에 존재하는 refresh 토큰인지 확인
        // - 존재하지 않다면 부당한 방법으로 탈취한 토큰
        Boolean isExist = refreshEntityRepository.existsByRefresh(refresh);
        if (!isExist) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }
        // 해당 토큰이 refresh 토큰인지 여부 확인
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        // refresh 토큰으로부터 사용자 정보 획득
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // access 토큰 재발급
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
        // refresh 토큰 재발급
        /*
        * rotate 이전의 refresh 토큰으로도 인증이 되기 때문에, 서버측에서 발급했던 refresh 토큰을 블랙리스트로 관리해야함
        * */
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        // Refresh 토큰 저장
        // 기존 Refresh 토큰 삭제 후 새로운 Refresh 토큰 저장
        refreshEntityRepository.deleteByRefresh(refresh);
        addRefreshEntity(username,newRefresh,86400000L);

        response.setHeader("access", newAccess);
        response.addCookie(jwtUtil.createCookie("refresh",newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
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
}
