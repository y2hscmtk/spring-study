package spring.springcorebasic.member;

import java.util.HashMap;
import java.util.Map;

// 개발단계에서 테스트 하기 위한 메모리 저장소
// 데이터베이스에 저장하는 것이 아니기 때문에 서버가 종료되면 초기화된다.
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
