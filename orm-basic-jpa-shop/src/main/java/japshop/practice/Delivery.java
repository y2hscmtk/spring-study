package japshop.practice;

import jakarta.persistence.*;

//@Entity
public class Delivery extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    // ORDERS 테이블과 1대1 연관관계
    // - 주테이블은 어디로 설정?
    @OneToOne(mappedBy = "delivery") // 일대일 연관관계 FK의 이름은 delivery(객체관점)
    private Order order;

    // 배송지 주소
    private String city;
    private String street;
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

}
