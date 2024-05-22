package utilizingjpa.jpashop.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import utilizingjpa.jpashop.domain.Order;
import utilizingjpa.jpashop.repository.OrderRepository;
import utilizingjpa.jpashop.repository.OrderSearch;

import java.util.List;

/**
 * xToOne (ManyToOne, OneToOne) 관계에서의 성능 최적화
 * Order 조회
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        // 엔티티 직접 노출시 지연로딩 문제 해결방법3
        // 엔티티 조회를 통해 연관된 객체 로딩(지연로딩 해제)
        for (Order order : all) {
            order.getMember().getName(); // 연관관계를 맺고있는 객체를 실제로 사용하려고 할 때 조회 쿼리가 생성되므로, LAZY 강제 초기ㅗ하가 된다.
            order.getDelivery().getAddress();
        }

        return all;
    }
}
