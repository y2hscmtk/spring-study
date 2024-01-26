package japshop.jpabook;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name ="ORDER_ITEM_ID")
    private Long id;
    @Column(name = "ORDER_ID")
    private Long memberID;
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
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
}
