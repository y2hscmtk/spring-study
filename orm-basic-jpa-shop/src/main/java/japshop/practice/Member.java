package japshop.practice;

import jakarta.persistence.*;
import japshop.section.Address;

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
//@Entity
public class Member extends BaseEntity{
    @Id
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;

    @Embedded
    private Address address; // 값 타입으로 지정

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
