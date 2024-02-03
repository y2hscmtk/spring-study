package japshop.practice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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
@Entity
public class Member extends BaseEntity{
    @Id
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    private String city;
    private String zipcode;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
