package com.example.new_jwt_practice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfo {
    private String username; // 사용자 이름
    private String role; // 권한
}
