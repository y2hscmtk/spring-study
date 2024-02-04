package japshop.section;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// 영속성 전이(CASCADE) 관련 예제
// 특정 엔티티를 영속 상태로 만들 때, 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때
@Entity
public class Parent {
    @Id
    @Column(name = "parent_id")
    @GeneratedValue
    private Long id;
    private String name;

    // mappedBy : pk(parent)에 의해서 연관관계 설정됨
    // cascade : 연관된 모든 엔티티 자동 영속화
    // orphanRemoval = true : 부모 객체와 연관관계가 끊어질 경우, 자식 엔티티 자동 삭제
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }

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

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
