package utilizingjpa.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utilizingjpa.jpashop.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // 다대다관계 - 예시용이므로 실무에서는 사용금지
    @ManyToMany
    @JoinTable(name = "category_item", // 중간 테이블을 생성해야함 => 중간 테이블에는 두 테이블의 키값만이 들어감
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    // 자기 연관관계 - 부모
    @ManyToOne(fetch = FetchType.LAZY) // 여러명의 자식에 한 명의 부모, XToOne은 지연로딩 설정해야함(디폴트가 EAGER임)
    @JoinColumn(name ="parent_id") // 조인 이후 컬럼명 parent_id
    private Category parent;

    // 자기 연관관계 - 자식 // 한 부모에 여러명의 자식
    @OneToMany(mappedBy = "parent") // 위쪽의 parent 필드를 통해 매핑
    private List<Category> child = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this); // 편의 메서드
    }

}
