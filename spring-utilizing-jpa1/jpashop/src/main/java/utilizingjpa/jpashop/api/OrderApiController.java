package utilizingjpa.jpashop.api;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utilizingjpa.jpashop.domain.Address;
import utilizingjpa.jpashop.domain.Order;
import utilizingjpa.jpashop.domain.OrderItem;
import utilizingjpa.jpashop.domain.OrderStatus;
import utilizingjpa.jpashop.repository.OrderRepository;
import utilizingjpa.jpashop.repository.OrderSearch;
import utilizingjpa.jpashop.repository.order.query.OrderFlatDto;
import utilizingjpa.jpashop.repository.order.query.OrderItemQueryDto;
import utilizingjpa.jpashop.repository.order.query.OrderQueryDto;
import utilizingjpa.jpashop.repository.order.query.OrderQueryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    // 엔티티를 직접 노출하는 경우(컬렉션 노출)
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        // Lazy Loading 처리(강제 초기화)
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            // 조회하고 싶은 정보 -> orderItems
            List<OrderItem> orderItems = order.getOrderItems();
            // Lazy Loading 처리(강제 초기화)
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    // 엔티티를 DTO로 변환
    // 컬렉션 내부의 모든 컬렉션들을 DTO로 변환한 뒤 리턴(엔티티 직접 노출 x)
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return collect;
    }

    // 패치조인을 활용한 성능 최적화
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());

        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            // 최소,최대 페이징 기준 설정
            @RequestParam(value = "offset",defaultValue = "0") int offset,
            @RequestParam(value = "limit",defaultValue = "100") int limit) {
        // Order - Member : ManyToOne
        // Order - Delivery : OneToOne
        // 우선 xToOne관계에 대해서는 페치조인을 통해 가져온다.
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        // Order - OrderItems : OneToMany
        // xToMany 관계에 대해서는 페치조인을 시도할 경우
        // '다'쪽을 기준으로 데이터 증가가 이루어지므로 페치조인하지 않는다.
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());

        return result;
    }

    @GetMapping("api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
        // 필요하다면 메모리단위에서 중복 제거 작업 수행 ~
        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                                o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                                o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }


    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        // OrderItem은 엔티티이기 때문에 프록시 초기화를 하지 않으면 조회되지 않는다.(Lazy)
        // 주의 : DTO안에 엔티티가 존재해서는 안된다.(엔티티를 반환해서는 안된다.)
        // private List<OrderItem> orderItems;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            // Proxy 초기화(Lazy Loading)
//            order.getOrderItems().stream().forEach(o -> o.getItem().getName());
//            orderItems = order.getOrderItems();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(toList());
        }
    }

    // 노출하고 싶은 부분만 노출하기 위해(엔티티를 노출하지 않기 위해) DTO를 작성한다.
    @Getter
    static class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;
        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }


}
