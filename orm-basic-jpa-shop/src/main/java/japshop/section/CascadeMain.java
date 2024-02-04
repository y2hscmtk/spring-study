package japshop.section;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class CascadeMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            // 연관관계 설정을 마친 후 영속 상태로 만들기 위해서 => persist를 해야함
//            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);
            // 위와 같이 Persist를 하는 것은 귀찮음 -> parent를 중심으로 parent를 persist하면 자동으로 child도 persist하고 싶음
            // Parent의 연관관계에 casecase = All 속성 작성
            em.persist(parent); // 부모만 persist 하여도 자식도 함께 persist 된다.

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0); // 0 번째 인덱스 컬렉션에서 제거

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
