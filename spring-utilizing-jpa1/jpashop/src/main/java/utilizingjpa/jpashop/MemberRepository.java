package utilizingjpa.jpashop;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    // JPA를 활용하여 DB에서 데이터 가져오는 역할을 수행
    @PersistenceContext // 스프링부트매니저가 EntityManger를 생성해줌
    private EntityManager em;

    // 저장
    public Long save(Member member) {
        em.persist(member);
        return member.getId(); // 커멘드와 쿼리 분리(리턴값은 가급적 아이디정도로(객체 그 자체를 리턴시키지않기))
    }

    // 조회
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
