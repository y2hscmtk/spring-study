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
            member.setUsername("member1");
            em.persist(member);

            Team team = new Team();
            team.setName("Team1");
            member.setTeam(team); // 팀 설정(임시)
            em.persist(team);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member.getId());

            // Member에서 Team과 연관관계 설정시 지연로딩으로 설정하였기때문에, 프록시 클래스로 연관된 것을 가져온다.
            System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());

            System.out.println("===============");
            m.getTeam().getName(); // 팀 엔티티가 필요한 시점에 쿼리를 생성한다.(실제 team을 사용하는 시점에 초기화) => 지연로딩(LAZY)
            System.out.println("===============");

            // 멤버와 팀을 매번 같이 사용한다면 => 즉시 로딩(EAGER)를 사용해서 한번에 TEAM과 조인하는 쿼리를 생성한다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
