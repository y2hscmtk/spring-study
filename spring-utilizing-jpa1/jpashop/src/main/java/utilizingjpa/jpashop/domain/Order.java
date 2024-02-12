package utilizingjpa.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 다대일
    @ManyToOne
    @JoinColumn(name = "member_id") // 매핑을 무엇으로 할 것인지(Member의 pk를 fk로 갖겠다.)
    private Member member; // FK를 가진쪽이 연관관계의 주인('다'에 속함)

    // 일대일 : FK의 위치는 아무곳에나 두어도 상관없으나 접근을 많이 하는 곳에 두는 것이 좋다.
    @OneToOne
    @JoinColumn(name = "delivery_id") // Delivery테이블의 PK를 FK로
    private Delivery delivery;

    // 일대다 => OrderItem쪽이 '다'
    @OneToMany(mappedBy = "order") // OrderItem 테이블의 pk order에 의해 매핑된다.
    private List<OrderItem> orderItems = new ArrayList<>();

    // 주문 시간
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // STRING 속성으로 지정 중요
    private OrderStatus status; // 주문 상태 ORDER, CANCEL


}
