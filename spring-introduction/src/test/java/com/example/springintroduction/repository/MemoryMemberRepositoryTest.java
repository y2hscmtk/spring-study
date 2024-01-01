package com.example.springintroduction.repository;

import com.example.springintroduction.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// 테스트 작성 원칙
// 테스트는 순서와 관계없이 진행되야 함
// 하나의 테스트가 끝난 이후에 데이터를 지우지 않으면, 다른 테스트에서 데이터 삽입시 중복 데이터 발견으로 인해 오류 발생 가능 => 순서에 따른 차이
// 따라서 테스트가 끝난 이후에 clear를 통해 데이터를 모두 지워줘야 함

// 테스트 이름은 클래스명 + Test를 붙이는것이 관습
class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 하나의 테스트가 끝날때마다 실행되는 함수 annotation
    @AfterEach
    public void afterEach() {
        repository.clearStore(); // store.clear 호출
    }


    // 저장 기능이 제대로 수행되는지 확인하기 위한 테스트 코드
    @Test
    public void save() {
        // 회원 생성
        Member member = new Member();
        member.setName("spring");

        // 회원 가입
        repository.save(member);

        // 검증
        Member result = repository.findById(member.getId()).get();
        //System.out.println("result = " + (result == member));

        //Assertions.assertEquals(member,null);
        // assertThat -> Assertions (import org.assertj.core.api.Assertions)
        // 두 객체가 같은 객체인지 검사
        assertThat(result).isEqualTo(member); // Option + Enter => Static Import : Assertions.assertThat 간소화
    }


    @Test
    public void findByName(){

        // 테스트용 회원 1 생성
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        // 테스트용 회원 2 생성
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // 회원1 이름으로 찾기
        Member result = repository.findByName("spring1").get();
        // 결과물이 회원1과 같은지 검증
        Assertions.assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("spring1");
        member2.setName("spring2");
        repository.save(member1);
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

    }

}
