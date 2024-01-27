package japshop;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team); // 영속상태가됨
//
//            Member member = new Member();
//            member.setUsername("member1");
//
//            // 멤버 1을 팀A에 소속시키고 싶은 상황
//            member.setTeam(team); // 객체지향적 관점에서의 매핑 -> 참조 사용
//            // => JPA가 알아서 join해준다.
//            em.persist(member);

            // Section 5-3 : 양방향 연관관계와 연관관계의 주인 2 - 주의점, 정리

            // 회원 생성
            Team team = new Team();
            team.setName("TeamA");
            // 역방향(주인이 아닌 방향)만 연관관계를 설정하는 실수 -> 역방향은 읽기전용이므로 의미가 없다.
            // team.getMembers().add(member); // 팀에 멤버 삽입 -> 잘못된 방식(연관관계의 주인은 member임 -> team의 FK를 Member가 소유함)
            em.persist(team);

            // 팀 생성
            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team); // 올바른 방식(연관관계의 주인은 Member)
            em.persist(member);


            em.flush(); // 영속성 컨텍스트 내용 DB에 반영
            em.clear(); // 영속성 컨텍스트 비우기

            // 조회할때 -> 참조를 꺼내면 됨
            // Member findMember = em.find(Member.class, member.getId());
//            Team findTeam = findMember.getTeam(); // 바로 꺼내어 쓸 수 있다.
//            System.out.println("findTeam.getName() = " + findTeam.getName());
            
            // 양방향 연관관계 조회
            // 멤버 -> 팀 -> 멤버
//            Member findMember = em.find(Member.class, member.getId());
//            List<Member> members = findMember.getTeam().getMembers();
//
//            for (Member m : members) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
