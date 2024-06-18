package com.example.new_jwt_practice.jwt.service;

import com.example.new_jwt_practice.entity.Member;
import com.example.new_jwt_practice.jwt.dto.CustomUserDetails;
import com.example.new_jwt_practice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


// username, password를 바탕으로 회원 엔티티를 조회하고, 조회결과를 바탕으로 UserDetails를 생성하여 반환
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if (optionalMember.isEmpty()) {
            return null;
        }
        Member member = optionalMember.get();

        // UserDetails를 구체화하는 클래스를 생성하여 반환
        return new CustomUserDetails(member);
    }
}
