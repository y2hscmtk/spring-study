package utilizingjpa.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import utilizingjpa.jpashop.domain.Member;

import java.util.List;

@Repository // 스프링 빈으로 등록
public class MemberRepository {
    @PersistenceContext // 스프링이 Entity Manger를 만들어서 주입
    private EntityManager em;

    // 회원 저장
    public void save(Member member) {
        em.persist(member); // 영속성 컨텍스트에 member 객체 삽입 -> 트랜잭션 커밋 시점에 반영(Insert Query)
    }

    // 일치하는 회원 반환
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 일치하는 회원 모두 반환
    // JPQL로 작성
    public List<Member> findAll() {
        // 엔티티 객체 대상으로 쿼리 생성
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 이름으로 회원 조회
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }


}
