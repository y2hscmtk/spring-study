package utilizingjpa.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("B") // dtype을 통해서 구분될 이름
public class Book extends Item {
    private String author;
    private String isbn;
}
