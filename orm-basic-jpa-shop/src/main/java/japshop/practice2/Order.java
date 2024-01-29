package japshop.practice2;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ----------------                ----------------
 * | MEMBER_ID(PK)|                | ORDER_ID(PK)  |
 * |--------------|  1 --------- m |-------------- |
 * | NAME         |                | MEMBER_ID(FK) |
 * | CITY         |                | ORDERDATE     |
 * | STREET       |                | STATUS        |
 * | ZIPCODE      |                -----------------
 * ---------------|
 * MEMBER 1 ----- m ORDERS
 * m 쪽이 연관관계의 주인
 */

/**
 * --------------------                ----------------
 * | ORDER_ITEM_ID(PK)|                | ORDER_ID(PK)  |
 * |------------------|  m --------- 1 |-------------- |
 * | ORDER_ID(FK)     |                | MEMBER_ID(FK) |
 * | ITEM_ID(FK)      |                | ORDERDATE     |
 * | ORDERPRICE       |                | STATUS        |
 * | COUNT            |                -----------------
 * -------------------|
 * ORDER_ITEM m --- 1 ORDERS
 * m 쪽이 연관관계의 주인 => 주인은 ORDER_ITEM
 */
//@Entity
//@Table(name = "ORDERS") // order는 예약어로 존재하므로 오류 발생 가능
public class Order {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    // ORDERS가 m에 속하므로, 연관관계의 주인으로 삼는다. Member의 FK소유
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID") // FK MEMBER_ID에 의해 JOIN된다.
    private Member member;

    // ORDERS 테이블과 ORDER_ITEM 테이블과의 관계에서 연관관계의 주인은 M에 해당하는 ORDER_ITEM
    @OneToMany(mappedBy = "ORDER_ID") // OrderItems의 FK ORDER_ID에 의해 매핑된다.
    private List<OrderItem> orderItems = new ArrayList<>();
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING) // 문자열로 두어야 추가 변경사항이 생겨도 안전함(인덱스가 아니므로 안전)
    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // 연관관계 편의 함수 예시
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
