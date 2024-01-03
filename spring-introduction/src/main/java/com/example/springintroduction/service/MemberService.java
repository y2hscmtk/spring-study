package com.example.springintroduction.service;

import com.example.springintroduction.domain.Member;
import com.example.springintroduction.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Command + Shift + T => 테스트 케이스 바로 만들기
// 싱글톤 객체로 스프링 컨테이너에 등록하기 위해 스프링 빈으로 등록해야 한다.
// @Service // Service annotation을 통해 스프링에게 스프링 빈 객체임을 알린다.
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;


    @Autowired // 스프링 컨테이너에 스프링 빈으로 등록된 memberRepository 를 찾아서 의존관계로 설정
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // result => Optional로 반환됨
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        // 반환된 Optional의 값이 null이 아니라면
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        // ctrl + t => 일부 코드를 통째로 함수화 할 수 있음
        validateDuplicatedMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원 검증 로직
    private void validateDuplicatedMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 특정 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
