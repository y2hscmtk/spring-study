package com.example.new_jwt_practice.repository;

import com.example.new_jwt_practice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
