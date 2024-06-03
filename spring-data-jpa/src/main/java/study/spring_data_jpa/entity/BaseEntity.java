package study.spring_data_jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


// Spring Data JPA의 @EnableJpaAuditing를 사용하여 엔티티 상태추적이 가능하다.

@EntityListeners(AuditingEntityListener.class) // 이벤트를 사용하여 추적
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity {

    // BaseTimeEntity
    // 작성시간, 수정시간 추적
    // 4가지 옵션을 모두 필요로 하지 않는 엔티티가 존재할 수 있기 때문에 분리하는 것이 좋다.

    // 등록자, 수정자 추적
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
