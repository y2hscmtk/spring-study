package utilizingjpa.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty // Validation => Null이 아닌 경우에 대해서만
    private String name; // 어떤 API에서는 NULL일수도 있는데, 어떤데에서는 아닐 수도 있는 문제

    @Embedded // 내장 타입
    private Address address;

    // 하나의 회원이 여러개의 주문을 할 수 있음 -> 일대다 연관관계
    // '일'에 속하므로 연관관계의 주인이 아닌 매핑되는 테이블임
    @JsonIgnore // JSON데이터로 엔티티 반환시, 해당 내용은 반환하지 않겠다는 의미
    @OneToMany(mappedBy = "member") // Order테이블의 필드명 member에 의해 매핑되었다는 의미(fk가 member다)
    private List<Order> orders = new ArrayList<>(); // '일'에 속하므로 단순 조회
}
