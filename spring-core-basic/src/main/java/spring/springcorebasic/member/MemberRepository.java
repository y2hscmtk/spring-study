package spring.springcorebasic.member;

// 아직 어떤 저장소를 사용할지 모르는 상황이기 때문에, 인터페이스로 설계한다.
public interface MemberRepository {

    // 새로운 회원 저장
    void save(Member member);

    // 회원 아이디로 DB에서 회원조회
    Member findById(Long memberId);
}
