package com.example.springintroduction.repository;

import com.example.springintroduction.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// @Repository 를 통해 스프링 빈 객체임을 알린다.
@Repository
public interface MemberRepository {
    Member save(Member member); // 회원가입 로직
    // Optional => Null Safe를 위해 사용
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
