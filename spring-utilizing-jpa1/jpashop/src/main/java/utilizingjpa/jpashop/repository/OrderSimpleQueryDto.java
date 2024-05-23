package utilizingjpa.jpashop.repository;

import lombok.Data;
import utilizingjpa.jpashop.domain.Address;
import utilizingjpa.jpashop.domain.Order;
import utilizingjpa.jpashop.domain.OrderStatus;

import java.time.LocalDateTime;

// JPA에서 DTO로 조회할때 사용할 DTO
@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
