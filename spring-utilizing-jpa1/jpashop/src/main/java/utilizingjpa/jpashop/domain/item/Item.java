package utilizingjpa.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utilizingjpa.jpashop.domain.Category;
import utilizingjpa.jpashop.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 싱글 테이블 타입으로 ~
@DiscriminatorColumn(name = "dtype") // 구분자 속성명 지정
public abstract class Item {
    // 상속받도록 할 필드들
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    // 다대다 관계(실무 사용 금지)
    @ManyToMany(mappedBy = "items") // items에 의해 매핑될 것이다.
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    //Setter를 사용하는 것이 아닌, 비즈니스 메소드를 별도로 작성하여 사용해야 한다.
    /**
     * stock 증가
     * */
    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    /**
     * stock 감소
     * */
    public void removeStock(int stockQuantity) {
        int restStock = this.stockQuantity - stockQuantity;
        // 0보다 작아지면 안됨
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
