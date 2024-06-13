package com.example.springsecuritystudy.service;

import com.example.springsecuritystudy.dto.JoinDTO;
import com.example.springsecuritystudy.entity.UserEntity;
import com.example.springsecuritystudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;

    // 회원가입 진행
    public void joinProcess(JoinDTO joinDTO) {

        // 저장할 사용자 생성
        UserEntity user = UserEntity.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .role("USER")
                .build();

        userRepository.save(user);
    }
}
