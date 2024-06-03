package study.spring_data_jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 엔티티에는 공통적으로 생성시간, 최종수정시간을 기록하는 것이 관리 측면에서 좋다.
// (필요하다면 작성자, 수정자 까지)
@MappedSuperclass // 엔티티의 입장에서 상속받을 수 있도록
@Getter @Setter
public class JpaBaseEntity {
    @Column(updatable = false) // 변경되지 못하게함
    private LocalDateTime createdDate; // 생성 시간
    private LocalDateTime updatedDate; // 최종 수정 시간

    // 순수 JPA를 활용한 해결방법
    // 영속화(Persist)하기전에 아래 함수가 호출되도록 할 수 있음
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    // Update 되기전에 호출된다.
    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

}
