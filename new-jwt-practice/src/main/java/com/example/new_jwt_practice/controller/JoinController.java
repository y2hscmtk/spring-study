package com.example.new_jwt_practice.controller;

import com.example.new_jwt_practice.dto.JoinDTO;
import com.example.new_jwt_practice.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<?> joinProcess(@RequestBody JoinDTO joinDTO) {
        // 회원 가입 진행
        return joinService.join(joinDTO);
    }

}
