package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// JPA에서 매핑되는 객체임을 명시 @Entity
@Entity
// @Table(name = "USER") // 만약 데이터베이스에 저장된 이름이 Member가 아니라 User일 경우 따로 이름을 명시한다.
public class Member {
    @Id // Primary Key임을 명시 @Id
    private Long id;

    // @Column(name = "username") => DB에서 name이 아닌 username으로 저장되어있다면 이렇게 명시해둔다.
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
