package japshop.section;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

// 임베디드 타입으로 묶어서 사용하기 위함
// 임베디드 타입은 Int, String과 같은 기본 값 타입(자바 클래스 개념)
// 주로 기본 값을 모아서 만든 복합 값이다.
// 데이터베이스 형태는 유지되고 객체의 형태만 달라진다.
// 객체적 관점에서 재사용성을 높일 수 있다는 장점이 있다.
@Embeddable // 임베디드 타입임을 알린다.
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // 기본 생성자 필수
    public Period(){

    }

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
