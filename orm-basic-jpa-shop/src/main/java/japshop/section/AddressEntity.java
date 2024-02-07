package japshop.section;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 값 타입 컬렉션 대안 -> 일대다 관계로 틀기
@Entity
@Table(name = "ADDRESS")
public class AddressEntity {
    @Id @GeneratedValue
    private Long id;

    public AddressEntity() {
    }

    public AddressEntity(String city,String street, String zipcode) {
        this.address = new Address(city, street, zipcode);
    }

    private Address address; // 값 타입

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
