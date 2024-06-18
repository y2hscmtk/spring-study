package com.example.new_jwt_practice.service;

import com.example.new_jwt_practice.dto.JoinDTO;
import com.example.new_jwt_practice.entity.Member;
import com.example.new_jwt_practice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<?> join(JoinDTO joinDTO) {

        // 동일 username 사용자 생성 방지
        if (memberRepository.existsMemberByUsername(joinDTO.getUsername())) {
            System.out.println("이미 존재하는 회원");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 존재하는 회원입니다.");
        }

        // 새로운 회원 생성
        Member member = Member.builder()
                .username(joinDTO.getUsername())
                // 비밀번호 암호화 해서 저장
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .role("ROLE_ADMIN") // 사용자 권한 설정 접두사 ROLE 작성 필요
                .build();

        System.out.println("회원 생성 완료");
        memberRepository.save(member);

        return ResponseEntity.ok("회원가입 성공");
    }
}
