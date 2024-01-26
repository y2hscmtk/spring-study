package japshop;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team); // 영속상태가됨

            Member member = new Member();
            member.setUsername("member1");

            // 멤버 1을 팀A에 소속시키고 싶은 상황
            member.setTeam(team); // 객체지향적 관점에서의 매핑 -> 참조 사용
            // => JPA가 알아서 join해준다.
            em.persist(member);

            em.flush(); // 영속성 컨텍스트 내용 DB에 반영
            em.clear(); // 영속성 컨텍스트 비우기

            // 조회할때 -> 참조를 꺼내면 됨
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam(); // 팀을 바로 꺼내어 쓸 수 있다.
            System.out.println("findTeam.getName() = " + findTeam.getName());
            

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
