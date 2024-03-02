package utilizingjpa.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자를 통한 생성 방지
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 매핑을 무엇으로 할 것인지(Member의 pk를 fk로 갖겠다.)
    private Member member; // FK를 가진쪽이 연관관계의 주인('다'에 속함)

    // 일대일 : FK의 위치는 아무곳에나 두어도 상관없으나 접근을 많이 하는 곳에 두는 것이 좋다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") // Delivery테이블의 PK를 FK로
    private Delivery delivery;

    // 일대다 => OrderItem쪽이 '다'
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // OrderItem 테이블의 pk order에 의해 매핑된다.
    private List<OrderItem> orderItems = new ArrayList<>();

    // 주문 시간
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // STRING 속성으로 지정 중요
    private OrderStatus status; // 주문 상태 ORDER, CANCEL


    // == 연관관계 메서드 ==//
    // 위치는 핵심적으로 컨트롤 되는 엔티티에 존재하는 것이 좋다.
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this); // 연관 관계 메서드 원자화
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /*// 기존의 비즈니스 로직 => 양방향 연관관계 설정시
    public static void main(String[] args) {
        Member member = new Member();
        Order order = new Order();

        // 양 방향 연관 관계 설정
        member.getOrders().add(order);
        order.setMember(member);

        // 연관관계 편의 메서드 => 2개의 메서드를 원자적으로 하나의 메서드가 되도록 설정
        // => 하나의 함수만으로 설정이 가능하고, 까먹을 위험도 없어진다.
    }*/

    //==생성 메소드==//
    //주문 상품은 연관관계가 복잡하게 얽혀있기 때문에 생성 메소드를 따로 두는데 편하다
    //회원 정보, 배송 정보, 아이템 여러개
    //=> 주문 생성에 대한 모든 로직 완료
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member); // 연관관계 설정
        order.setDelivery(delivery); // 연관관계 설정
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); // 기본 정보 주입
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        // 이미 배송 완료 => 취소 불가
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료 된 상품은 취소가 불가능합니다");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     * */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
