package utilizingjpa.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utilizingjpa.jpashop.domain.Category;

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
}
