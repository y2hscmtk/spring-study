package japshop.practice;

import jakarta.persistence.*;

import javax.lang.model.type.IntersectionType;
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
//@Entity
// Item만 단독으로 클래스에 저장할 일이 없다고 판단된다면 추상 클래스로
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 전략은 SINGLE_TABLE로
//@DiscriminatorColumn // 상속받은 클래스들 사이에서 어떤 아이템인지 구별하기 위한 속성의 이름, 기본값은 DTYPE
public abstract class Item extends BaseEntity{
    @Id
    @Column(name = "ITEM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 사용자가 키 값을 입력하지 않아도 자동으로 키값을 생성함
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    // Item은 연관관계의 주인이 아님(데이터베이스 설계상)
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
