package utilizingjpa.jpashop.api;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import utilizingjpa.jpashop.domain.Address;
import utilizingjpa.jpashop.domain.Order;
import utilizingjpa.jpashop.domain.OrderItem;
import utilizingjpa.jpashop.domain.OrderStatus;
import utilizingjpa.jpashop.repository.OrderRepository;
import utilizingjpa.jpashop.repository.OrderSearch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

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
                .collect(Collectors.toList());
        return collect;
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
                    .collect(Collectors.toList());
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
