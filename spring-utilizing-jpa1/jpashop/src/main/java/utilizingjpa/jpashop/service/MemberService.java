package utilizingjpa.jpashop.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilizingjpa.jpashop.domain.Member;
import utilizingjpa.jpashop.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA의 변경 로직은 모두 Transaction 안에서 수행되어야 한다.
// readOnly의 경우 읽기만 가능하므로 최적화에 도움이 된다. 하지만 수정 같은 작업이 필요할 경우 readOnly를 false해야한다.
// Spring에서 제공하는 @Transactional을 사용하는 것이 좋다.
@AllArgsConstructor
@RequiredArgsConstructor // final 키워드가 존재하는 필드에 대해서 생성자를 만들어준다. => 생성자 주입
public class MemberService {
    //1.  필드 injection
    private final MemberRepository memberRepository;

    // 2. setter 주입 -> 테스트 할 경우 다른 레파지토리를 런타임에 주입할 수 있다는 장점
    // 하지만 런타임에 변경할 일이 거의 존재하지 않는다.
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    // 3. 생성자 주입
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional // 디폴트는 readOnly = false 이므로,
    // DB의 값을 직접적으로 수정하는 로직의 경우 Transcational 어노테이션을 추가로 작성한다.
    // 작성하지 않을경우 최상단의 @Transadtional(readOnly = true)가 적용된다.
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); // 값이 있다는 것을 보장 -> 영속성 컨텍스트의 키 값은 PK가 되므로
    }

    // 중복 회원 검증 로직
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        //EXCEPTION
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    // 회원 전체 조회
    // 읽기가 아닌 경우는 readOnly를 설정하면 안된다.
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
