package utilizingjpa.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utilizingjpa.jpashop.domain.item.Item;

@Entity
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // 다대일 '다' ; 연관관계의 주인
    @ManyToOne
    @JoinColumn(name = "order_id") // 매핑 이후 order_id라는 pk를 얻음
    private Order order;

    // 다대일 '다' ; 연관관계의 주인
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량
}
