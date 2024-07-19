package com.example.oauth2_jwt_api.member.repository;

import com.example.oauth2_jwt_api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByUsername(String username);
}
