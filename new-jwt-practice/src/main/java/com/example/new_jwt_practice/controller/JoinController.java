package com.example.new_jwt_practice.controller;

import com.example.new_jwt_practice.dto.JoinDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    @PostMapping("/join")
    public ResponseEntity<?> joinProcess(@RequestBody JoinDTO joinDTO) {

        return ResponseEntity.ok("회원가입 완료");
    }

}
