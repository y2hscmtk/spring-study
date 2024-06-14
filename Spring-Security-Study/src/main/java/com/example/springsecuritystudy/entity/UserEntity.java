package com.example.springsecuritystudy.entity;

import jakarta.persistence.*;
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

    @Column(unique = true) // 중복 방지용
    private String username;
    private String password;

    private String role; // 사용자 권한 확인용
}
