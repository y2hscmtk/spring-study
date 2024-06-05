package study.spring_data_jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {

    // 이 값은 객체가 생성되는 시점에 부여된다. => 따라서 새로운 엔티티인지 아닌지 판단하는 기준으로 사용 가능하다.
    @CreatedDate
    private LocalDateTime createdDate;

    // save()의 판단 기준을  Persistable 인터페이스를 구현하여 직접 부여할 수 있다.
    @Override
    public boolean isNew() {
        // 새로운 엔티티인지 아닌지 여부를 확인 할 수 있다.
        // return false;
        return createdDate == null; // createdDate가 존재하는지 아닌지를 새로운 엔티티로서의 판단 기준으로 설정한다.
        // => 더 효율적으로 save()를 동작시킬 수 있다.
    }

    @Id
//    @GeneratedValue
    private String id;

    public Item(String id) {
        this.id = id;
    }
}
