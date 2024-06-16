package com.example.springsecuritystudy.dto;

import com.example.springsecuritystudy.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    // 사용자의 권한을 반환한다. // 데이터베이스에 저장한 ROLE에 해당한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole(); // 사용자의 권한 리턴
            }
        });

        return collection;
    }

    // 사용자 비밀번호를 반환한다.
    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    // 사용자 username을 반환한다.
    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }
}
