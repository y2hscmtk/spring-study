package com.example.springsecuritystudy.dto;

import lombok.Data;

// 회원가입을 위한 사용자 정보 DTO
@Data
public class JoinDTO {
    private String username;
    private String password;
}
