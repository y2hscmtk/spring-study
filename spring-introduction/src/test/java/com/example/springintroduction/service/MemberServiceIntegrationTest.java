package com.example.springintroduction.service;

import com.example.springintroduction.domain.Member;
import com.example.springintroduction.repository.MemberRepository;
import com.example.springintroduction.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

// Command + Shift + T => 테스트 케이스 자동 생성
@SpringBootTest // 스프링 컨테이너와 테스트를 함게 실행한다.
@Transactional // 테스트 케이스에 이 어노테이션이 있으면,테스트 시작 전에 트랜잭션을 시작하고
    // 테스트 완료 후에 항상 롤백한다. => DB에 데이터가 남지 않게되므로 다음 테스트에 영향을 주지않는다.
    // (테스트 케이스의 경우에만 롤백한다.)
class MemberServiceIntegrationTest {
    // 테스트 단계는 가장 마지막 단계이므로 Constructor Injection을 해도 좋지만,
    // Filed Injection을 해도 상관없다.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원_가입_성공() {
        //given
        Member member = new Member();
        member.setName("hello"); // 데이터 베이스에 데이터가 남아있게되므로 오류가 발생하게됨
        // @Transactional을 활용하면 테스트가 끝난후 commit하지 않고 롤백하기 때문에
        // 테스트를 반복 수행해도 오류가 없다.

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