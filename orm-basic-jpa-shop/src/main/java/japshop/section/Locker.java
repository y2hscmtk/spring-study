package japshop.section;

import jakarta.persistence.*;

@Entity
public class Locker {
    @Id @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;
    private String name;

    //일대일 관계 양방향 연관관계 설정
    @OneToOne(mappedBy = "locker") // Member 테이블의 외래키 locker에 의해 매핑
    private Member member;
}
