package com.example.Spring_OAuth2_session.repository;

import com.example.Spring_OAuth2_session.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 존재하는 회원인지 확인
    Optional<Member> findByUsername(String username);
}
