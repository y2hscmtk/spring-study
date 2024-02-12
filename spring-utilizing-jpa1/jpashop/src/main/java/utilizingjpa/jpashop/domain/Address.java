package utilizingjpa.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
// 값 타입은 불변(변경할수 없음)으로 설계해야 한다.
public class Address {
    private String city;
    private String street;
    private String zipcode;

    // 값 타입은 기본 생성자가 필요한데, public으로 설정시 우리의 의도와 다르게 작용 가능하므로
    // Protected까지 허용한다.
    protected Address(){}

    // 값 타입의 좋은 설계는 생성 시점에만 허용하는 것이 좋다.
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
