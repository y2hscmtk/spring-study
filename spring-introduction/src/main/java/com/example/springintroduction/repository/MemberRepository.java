package com.example.springintroduction.repository;

import com.example.springintroduction.domain.Member;
import org.apache.commons.logging.Log;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 회원가입 로직
    // Optional => Null Safe를 위해 사용
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
