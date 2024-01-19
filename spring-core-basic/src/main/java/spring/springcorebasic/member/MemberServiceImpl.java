package spring.springcorebasic.member;

import org.springframework.stereotype.Component;

// 관례상 구현체는 인터페이스 이름 + Impl
// 새로운 회원을 가입(join)하고, 조회(findMember)하는 기능 구현 필요
// 위 기능을 위해 저장소(MemberRepository)에 대한 참조가 필요
@Component // 컴포넌트 스캔 대상으로 설정
public class MemberServiceImpl implements MemberService{

    // new 키워드를 사용하여 인터페이스에 대한 구현체 설정
    // => 구현체가 변경될 경우 코드 수정이 필요하므로 SOLID 원칙중 DIP(의존관계 역전 원칙)을 위배함
    // DIP : 구현에 의존하지 않고, 역할에 의존해야한다. => New 키워드를 사용한 구현체에 직접 의존하면 안된다.
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository; // DIP 만족을 위해

    //@Autowired // 의존관계 자동주입(컴포넌트 스캔시) == ac.getBean(MemberRepository.class) => 생성자 1개일 경우 생략 가능
    // 생성자 주입
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // @Configuration 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
