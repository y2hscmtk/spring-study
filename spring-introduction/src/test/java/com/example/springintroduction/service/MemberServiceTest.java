package com.example.springintroduction.service;

import com.example.springintroduction.domain.Member;
import com.example.springintroduction.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// Command + Shift + T => 테스트 케이스 자동 생성
class MemberServiceTest {

    MemoryMemberRepository memberRepository;
    MemberService memberService;

    // 테스트가 동작하기 이전에 수행
    // 매번 생성하기때문에 독립적인 효과를 가질 수 있다.
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }


    // 메모리 비우기 => 순서 영향 없애기 위해서
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입성공() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);;

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName()); //Option + Enter => Static Method Import
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member = new Member();
        member.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member);
        try {
            memberService.join(member2);
            fail(); // 실패할경우
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        // 원하는 오류가 발생했는지 확인
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));

//        memberService.join(member);
//        memberService.join(member2);

        //then
    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}