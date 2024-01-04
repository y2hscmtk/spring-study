package com.example.springintroduction.repository;

import com.example.springintroduction.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository를 상속받은 인터페이스를 만들어두면 스프링이 알아서 구현체를 만들어서 스프링 빈으로 등록해준다.
public interface SpringDataJpaMemberRepository extends
        JpaRepository<Member, Long>,MemberRepository {
    @Override
    Optional<Member> findByName(String name);
}
