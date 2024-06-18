package com.example.new_jwt_practice.service;

import com.example.new_jwt_practice.dto.JoinDTO;
import com.example.new_jwt_practice.entity.Member;
import com.example.new_jwt_practice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final MemberRepository memberRepository;

    public ResponseEntity<?> join(JoinDTO joinDTO) {
        
        // 동일 username 사용자 생성 방지 작성 필요

        // 새로운 회원 생성
        Member member = Member.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .build();

        memberRepository.save(member);

        return ResponseEntity.ok("회원가입 성공");
    }
}
