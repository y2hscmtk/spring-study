package utilizingjpa.jpashop.repository.order.query;

import lombok.Data;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import utilizingjpa.jpashop.domain.Address;
import utilizingjpa.jpashop.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderFlatDto {
    // Order와 OrderItem을 조회하여 한방 쿼리로 만든 후 조회
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
