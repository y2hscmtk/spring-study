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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // 매핑 이후 order_id라는 pk를 얻음
    private Order order;

    // 다대일 '다' ; 연관관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //==생성 메소드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 주문한 만큼 상품 재고를 줄여야함
        return orderItem;
    }


    //==비즈니스 로직==//
    // 주문 상품에도 취소 상태를 해줘야 함
    public void cancel() {
        getItem().addStock(count);
    }

    /**
     * 주문 상품 전체 가격 조회
     * */
    public int getTotalPrice() {
        return getOrderPrice() * getCount(); // 수량 * 가격
    }
}
