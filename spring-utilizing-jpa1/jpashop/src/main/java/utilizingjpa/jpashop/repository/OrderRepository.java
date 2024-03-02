package utilizingjpa.jpashop.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;
import utilizingjpa.jpashop.domain.Order;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    // 저장
    public void save(Order order) {
        em.persist(order);
    }

    // 조회
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // 모든 상품 조회
//    public List<Order> findAll(OrderSearch orderSearch) {
//
//    }
}
