package com.example.springintroduction;

import com.example.springintroduction.repository.JdbcMemberRepository;
import com.example.springintroduction.repository.MemberRepository;
import com.example.springintroduction.repository.MemoryMemberRepository;
import com.example.springintroduction.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// 자바 코드로 스프링 빈 등록하는 방법
@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }


    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }

}
