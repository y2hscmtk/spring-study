package japshop.section;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

// 모든 테이블에 공통되는 속성이 있다고 가정하자.(예 : id, name, 등)
// 객체지향적 개발관점에서 공통되는 값들은 상속을 통해서 해결하고 싶다.
// => Mapped SuperClass를 통해 해결 가능하다.

@MappedSuperclass // 매핑 정보만 받는 슈퍼 클래스
public class BaseEntity {
    // BaseEntity를 상속받는 모든 테이블에 아래 속성을 넣고 싶음
    @Column(name = "INSERT_MEMBER") // 저장될 컬럼의 이름도 변경 가능함
    private String createBy;
    private LocalDateTime createDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
