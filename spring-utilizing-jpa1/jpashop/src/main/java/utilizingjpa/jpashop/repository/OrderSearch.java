package utilizingjpa.jpashop.repository;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import utilizingjpa.jpashop.domain.Order;
import utilizingjpa.jpashop.domain.OrderStatus;
import utilizingjpa.jpashop.service.OrderService;

@Getter @Setter
@RequiredArgsConstructor
public class OrderSearch {
    private String memberName; // 회원 이름
    private OrderStatus orderStatus; // 주문 상태
}
