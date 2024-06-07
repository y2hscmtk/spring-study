package study.spring_data_jpa.repository;

import org.springframework.beans.factory.annotation.Value;

// Projections
// 엔티티 대신 DTO를 편하게 조회하고 싶을 때 사용한다. => Select 절에 들어갈 필드들에 대해 정의한다.
// 예) 전체 엔티티가 아닌 회원 이름만 조회하고 싶다면?
public interface UsernameOnly {

    @Value("#{target.username + ' ' + target.age}") // open projection => 데이터를 얻어온 후 원하는 형태로 변환한다.(엔티티를 다 가져온 후)
    String getUsername();
}
