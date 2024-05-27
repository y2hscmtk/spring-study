package utilizingjpa.jpashop.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// OrderRepository : Order 엔티티를 조회하기 위함 => 핵심 비즈니스 로직
// OrderQueryRepository : 화면, API에 의존 관계가 있는 전용 쿼리 레파지토리
@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders();

        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });

        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                        "select new utilizingjpa.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id,i.name,oi.orderPrice,oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }


    public List<OrderQueryDto> findOrders() {
        return em.createQuery(
                        "select new utilizingjpa.jpashop.repository.order.query.OrderQueryDto(o.id,m.name,o.orderDate,o.status,d.address) " +
                                "from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        // id 얻어오기
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());

        // id를 바탕으로 in 쿼리 생성
        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new utilizingjpa.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id,i.name,oi.orderPrice,oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));

        // 메모리에서 매칭하여 값 세팅
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    // 쿼리 한번으로 조회하는 방법
    // 페이징 불가능(조인으로 인해 중복 데이터가 발생하므로)
    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery(
                "select new " +
                        "utilizingjpa.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                            " from Order o" +
                            " join o.member m " +
                            " join o.delivery d " +
                            " join o.orderItems oi " +
                            " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
