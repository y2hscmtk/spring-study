package japshop.section;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속관계매핑설정 JOINED,SINGLE_TABLE,TABLE_PER_CLASS등
@DiscriminatorColumn // DTYPE을 만들어줌 => 아아템 테이블에서 각 아이템이 어떤 타입인지 알 수 있게 하기 위함
public class Item {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
