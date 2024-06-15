package com.example.springsecuritystudy.service;

import com.example.springsecuritystudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 스프링 시큐리티에서 인증과 인가 처리를 서비스 계층
// UserDetailsService를 상속받아 구현한다.

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    // 실제 사용자 정보를 불러오기 위한 레포지토리 계층
    private final UserRepository userRepository;

    // UserDetailsService 를 구체화한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
