package com.example.new_jwt_practice.repository;

import com.example.new_jwt_practice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Boolean existsMemberByUsername(String username);

    Optional<Member> findByUsername(String username);
}
