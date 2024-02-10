package utilizingjpa.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("A") // dtype을 통해서 구분될 이름
public class Album extends Item {
    private String artist;
    private String etc;
}
