package utilizingjpa.jpashop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import utilizingjpa.jpashop.domain.Member;
import utilizingjpa.jpashop.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 테스트에서 Transcation 어노테이션 작성시 자동 롤백을 수행한다.
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    // 테스트 종료 이후 롤백하기 때문에 Rollback false를 해야 영속성 컨텍스트를 flush함 => DB반영
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member); // database transcation이 commit된 시점에 반영

        //then
        assertEquals(member,memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member);
        //같은 이름으로 회원가입 시도
        try {
            memberService.join(member2); // 예외가 발생 해야함
        } catch (IllegalStateException e) {
            return; // 오류 발생시 리턴
        }

        //then
        fail("예외가 발생해야 한다"); // 이전에 종료돼야 하는데 fail에 도착하였다면 오류가 난 것임
    }



    
}