package com.example.springsecuritystudy.service;

import com.example.springsecuritystudy.dto.CustomUserDetails;
import com.example.springsecuritystudy.entity.UserEntity;
import com.example.springsecuritystudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// 스프링 시큐리티에서 인증과 인가 처리를 서비스 계층
// UserDetailsService를 상속받아 구현한다.

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    // 실제 사용자 정보를 불러오기 위한 레포지토리 계층
    private final UserRepository userRepository;

    // UserDetailsService 를 구체화한다.
    // username => 사용자 이름에 해당한다.
    // => 데이터베이스에 접근하여 인증(로그인)절차를 수행한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();

            // 조회된 사용자 정보를 바탕으로 UserDetails를 생성하여 반환한다.
            return new CustomUserDetails(userEntity);
        }

        // 사용자 정보가 없는 경우에 해당
        return null;
    }
}
