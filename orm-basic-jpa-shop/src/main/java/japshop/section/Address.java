package japshop.section;

import jakarta.persistence.Embeddable;

import java.util.Objects;

// 재사용성을 높이기위한 임베디드 타입으로 정의
@Embeddable
public class Address {
    private String citiy;
    private String zipcode;
    private String street;

    // 기본 생성자 필수
    public Address() {

    }

    public Address(String citiy, String zipcode, String street) {
        this.citiy = citiy;
        this.zipcode = zipcode;
        this.street = street;
    }

    public String getCitiy() {
        return citiy;
    }

    public void setCitiy(String citiy) {
        this.citiy = citiy;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    // 동등 비교를 위해 제대로 구현되어 있어야함
    // 객체의 equals는 모든값들이 동일한지 비교한다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(citiy, address.citiy) && Objects.equals(zipcode, address.zipcode) && Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citiy, zipcode, street);
    }
}
