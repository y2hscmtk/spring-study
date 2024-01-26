package japshop.jpabook;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//@Entity
//@Table(name = "ORDERS")
public class Order {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;
    @Column(name = "MEMBER_ID")
    private Long memberId;
    private LocalDateTime orderDate; // 주문 시간
    @Enumerated(EnumType.STRING) // 기본 타입이 순서이므로 문자열로 바꿔둬야함
    private OrderStatus status; // 주문상태(enum)
}
