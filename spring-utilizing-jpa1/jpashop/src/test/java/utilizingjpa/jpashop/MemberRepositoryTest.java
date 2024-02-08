package utilizingjpa.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional // DB의 데이터 수정,삽입 등과 관련된 작업은 트렌젝션 안에서 수행되어야한다.
    @Rollback(value = false) //false 설정시 테스트일지라도 커밋기록을 롤백하지 않는다.
    public void testMember() throws Exception {
        //given - 회원 객체를
        Member member = new Member();
        member.setUsername("memberA");
        //when - 저장시도하고
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then - 잘 저장되는지 확인
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);

        // 테스트가 종료되면 모든 커밋기록은 롤백한다. -> 데이터베이스에 반영하지 않는다.

    }
}