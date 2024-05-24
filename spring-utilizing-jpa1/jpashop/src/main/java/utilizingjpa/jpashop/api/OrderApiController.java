package utilizingjpa.jpashop.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import utilizingjpa.jpashop.domain.Order;
import utilizingjpa.jpashop.domain.OrderItem;
import utilizingjpa.jpashop.repository.OrderRepository;
import utilizingjpa.jpashop.repository.OrderSearch;

import java.util.List;

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
}
