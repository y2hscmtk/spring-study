package hellojpa;

import jakarta.persistence.*;

import java.util.Date;

// JPA에서 매핑되는 객체임을 명시 @Entity
/**
 * 요구사항 추가
 * 1. 회원은 일반 회원과 관리자로 구분해야 한다.
 * 2. 회원 가입일과 수정일이 있어야 한다.
 * 3. 회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이 제한이 없다.
 * */
@Entity
// @Table(name = "USER") // 만약 데이터베이스에 저장된 이름이 Member가 아니라 User일 경우 따로 이름을 명시한다.
@SequenceGenerator(name = "member_seq_generator",sequenceName = "member_seq")
public class Member {
    @Id // Primary Key임을 명시 @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "member_seq_generator")
    private Long id; // pk 매핑

    // @Column(name = "username") => DB에서 name이 아닌 username으로 저장되어있다면 이렇게 명시해둔다.
    @Column(name = "name") // 객체에서는 username이라고 쓰고 싶은데, DB에서는 name이라고 써야하는 경우
    private String username;

    private Integer age;

    // DB에는 EnumType이 없기 때문에 Enumerated를 써야함
    @Enumerated(EnumType.STRING) // String타입을 지정하지 안으면, Enum이 변경될 경우에도 여전히 인덱스로 유지 -> 오류
    private RoleType roleType;

    // 날짜, 시간, 날짜시간
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob // VARCHAR를 넘어서는 큰 데이터일때
    private String description;

    @Transient // DB에는 포함시키지 않을 속성 => DB와 관계없이 객체에서만 사용하고 싶음(메모리에서만 사용)
    private int temp;

    // 기본적으로 기본 생성자가 하나 필요함
    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.username = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + username + '\'' +
                '}';
    }
}
