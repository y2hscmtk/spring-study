package japshop.section;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("hello");

            em.persist(member);

            em.flush();
            em.clear();

            //Member findMember = em.find(Member.class, member.getId());
            //System.out.println("findMember.getId() = " + findMember.getId());
            //System.out.println("findMember.getUsername() = " + findMember.getUsername());

            // getReference()사용시
            Member referenceMember = em.getReference(Member.class, member.getId());
            // 아래와 같이 실제로 값이 필요하기 전까지 Select쿼리를 생성하지 않는다.
            System.out.println("referenceMember = " + referenceMember);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
