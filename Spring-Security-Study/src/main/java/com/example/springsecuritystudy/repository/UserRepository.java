package com.example.springsecuritystudy.repository;

import com.example.springsecuritystudy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
