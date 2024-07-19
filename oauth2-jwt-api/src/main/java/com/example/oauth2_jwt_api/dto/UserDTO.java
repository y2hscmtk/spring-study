package com.example.oauth2_jwt_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String role;
    private String name;
    private String username;
}
