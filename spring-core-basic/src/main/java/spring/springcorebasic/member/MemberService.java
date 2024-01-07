package spring.springcorebasic.member;

// - 회원을 가입하고 조회할 수 있다. => MemberService 로직
public interface MemberService {
    void join(Member member); // 새로운 회원 가입
    Member findMember(Long memberId); // 아이디로 회원 조회

}
