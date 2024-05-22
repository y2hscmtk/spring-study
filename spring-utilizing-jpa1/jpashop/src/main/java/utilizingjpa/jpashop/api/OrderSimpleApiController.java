package utilizingjpa.jpashop.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import utilizingjpa.jpashop.domain.Address;
import utilizingjpa.jpashop.domain.Order;
import utilizingjpa.jpashop.domain.OrderStatus;
import utilizingjpa.jpashop.repository.OrderRepository;
import utilizingjpa.jpashop.repository.OrderSearch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    // 엔티티를 직접 노출시켜 반환하는 경우, 연관관계 매핑된 엔티티가 지연로딩 엔티티라면 프록시를 반환할 수 없어서 문제가 발생한다.
    // 해결방안으로 로직에서 연관된 엔티티를 조회하여 LAZY를 강제 초기화 하는 방법과
    // Hibernate5Module 라이브러리를 사용하여 프록시를 무시하도록 설정하는 방법이 있다.
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        // 엔티티 직접 노출시 지연로딩 문제 해결방법3
        // 엔티티 조회를 통해 연관된 객체 로딩(지연로딩 해제)
        for (Order order : all) {
            order.getMember().getName(); // 연관관계를 맺고있는 객체를 실제로 사용하려고 할 때 조회 쿼리가 생성되므로, LAZY 강제 초기화가 된다.
            order.getDelivery().getAddress();
        }

        return all;
    }

    @GetMapping("api/v2/simple-orders")
    public List<SimpleOrdersDto> ordersV2() {
        // 엔티티를 그 자체로 반환하지 않고 DTO를 만들어 DTO를 반환한다.
        // N + 1 문제 발생 -> 1 + N => 1번째 쿼리가 생성됨(전체 조회 쿼리 => 반환값 N(2개)) => 1번째 쿼리의 결과로 추가 쿼리가 N개 발생
        // 1 + 회원 N + 배송 N
        return orderRepository.findAllByString(new OrderSearch()).stream()
                .map(o -> new SimpleOrdersDto(o))
                .collect(Collectors.toList());
    }

    @Data
    static class SimpleOrdersDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrdersDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화(실제 값을 조회하는 상황이므로 쿼리를 생성함)
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }

}
