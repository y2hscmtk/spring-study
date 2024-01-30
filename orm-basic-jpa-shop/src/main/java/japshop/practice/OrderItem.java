package japshop.practice;

import jakarta.persistence.*;

/**
 * --------------------                -----------------
 * | ORDER_ITEM_ID(PK)|                | ORDER_ID(PK)  |
 * |------------------|  m --------- 1 |-------------- |
 * | ORDER_ID(FK)     |                | MEMBER_ID(FK) |
 * | ITEM_ID(FK)      |                | ORDERDATE     |
 * | ORDERPRICE       |                | STATUS        |
 * | COUNT            |                -----------------
 * --------------------
 * ORDER_ITEM m --- 1 ORDERS
 * m 쪽이 연관관계의 주인 => 주인은 ORDER_ITEM
 */

/**
 * --------------------                -----------------
 * | ORDER_ITEM_ID(PK)|                | ITEM_ID(PK)   |
 * |------------------|  m --------- 1 |-------------- |
 * | ORDER_ID(FK)     |                | NAME          |
 * | ITEM_ID(FK)      |                | PRICE         |
 * | ORDERPRICE       |                | STOCKQUANTITY |
 * | COUNT            |                -----------------
 * --------------------
 * ORDER_ITEM m --- 1 ITEM
 * m 쪽이 연관관계의 주인 => 주인은 ORDER_ITEM
 */
//@Entity
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ITEM_ID") //FK의 이름은 ITEM_ID
    private Item item;
    @ManyToOne
    @JoinColumn(name = "ORDER_ID") //FK의 이름은 ORDER_ID
    private Order order;
    private int orderPrice;
    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
