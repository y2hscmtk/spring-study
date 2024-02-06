package japshop.section;

import jakarta.persistence.Embeddable;

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
}
