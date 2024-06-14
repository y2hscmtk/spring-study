package com.example.springsecuritystudy.repository;

import com.example.springsecuritystudy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 회원 이름 중복 방지용
    boolean existsByUsername(String username); // 해당하는 회원이 존재하는지 확인
}
