package com.example.springsecuritystudy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERENTITY")
public class UserEntity {

    @Id @GeneratedValue
    private Long id;

    private String username;
    private String password;

    private String role; // 사용자 권한 확인용
}
