package japshop.jpabook;

import jakarta.persistence.*;

//@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) // DB가 만들어주는 값으로 아이디 지정
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }
}
