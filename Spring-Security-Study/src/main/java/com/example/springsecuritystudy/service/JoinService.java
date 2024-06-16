package com.example.springsecuritystudy.service;

import com.example.springsecuritystudy.dto.JoinDTO;
import com.example.springsecuritystudy.entity.UserEntity;
import com.example.springsecuritystudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;

    // 회원가입시 사용자 비밀번호 암호화
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입 진행
    public void joinProcess(JoinDTO joinDTO) {

        // 회원 중복 검증
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if (isUser) {
            return;
        }

        // 저장할 사용자 생성
        UserEntity user = UserEntity.builder()
                .username(joinDTO.getUsername())
                .password(passwordEncoder.encode(joinDTO.getPassword())) // 비밀번호 암호화
                .role("ROLE_ADMIN") // 권한 부여시 "ROLE_" 접두사를 앞에 붙여주어야 한다.
                .build();

        userRepository.save(user);
    }
}
