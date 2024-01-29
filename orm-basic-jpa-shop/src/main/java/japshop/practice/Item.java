package japshop.practice;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
@Entity
public class Item {
    @Id
    @Column(name = "ITEM_ID")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    // Item은 연관관계의 주인이 아님(데이터베이스 설계상)
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
