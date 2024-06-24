package com.example.Spring_OAuth2_session.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
public class Member { // OAuth2 로그인에 성공한 회원을 저장하기 위한 엔티티
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String role;

    public void changeEmail(String email) {
        this.email = email;
    }
}
