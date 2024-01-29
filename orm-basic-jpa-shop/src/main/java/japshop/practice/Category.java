package japshop.practice;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id @GeneratedValue
    private Long id;

    private String name;

    // 자기 자신과 매핑
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent") // fk : parent와 연관되는 자기 연관관계
    private List<Category> child = new ArrayList<>();

    @ManyToMany // 실무에서는 쓰면 안됨(중간 객체를 만들어야 한다.)
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"), // 조인컬럼은 카테고리 아이디이고,
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID") // 반대쪽(아이템)은 아이템 아이디로 조인한다.
    )
    private List<Item> items = new ArrayList<>();
}
