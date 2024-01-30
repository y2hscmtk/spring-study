package japshop.section5_6;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("A") // DTYPE에서 저장될 이름 지정(구분자, 디폴트는 테이블 이름)
public class Album extends Item{
    private String artist;
}
