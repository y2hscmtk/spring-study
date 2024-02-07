package japshop.practice;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable // 값 타입으로 설정
public class Address {
    private String city;
    private String street;
    private String zipcode;

    // 값 타입을 사용하면 Address와 관련된 의미있는 메소드를 관리하기 편하다

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    // 값 타입의 비교는 동등 비교이므로 equals를 재정의해야한다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipcode);
    }
}
