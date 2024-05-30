package study.spring_data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.spring_data_jpa.entity.Member;

import java.util.List;

/**
 * 자세한 문법은 Spring.io - Projects - Spring Data - Reference Doc 참고
 * https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
 * 모든 기능이 다 되는 것은 아니다. => 필요한 기능이 있다면 별도로 만들어야한다.
 * 조회해야 하는 요소가 2개 이상이라면(매개변수가 2개 이상이라면) 쿼리를 사용하는 것을 추천한다.
 * */

// Repository 어노테이션을 적지 않아도 괜찮다.
public interface MemberRepository extends JpaRepository<Member,Long> {
    // 쿼리 메소드
    List<Member> findByUsername(String username); // 별도로 구현하지 않아도 Spring Data JPA에 의해 구현된다.

    // name과 이름이 일치하면서, 나이가 age이상인 회원 조회
    List<Member> findByUsernameAndAgeGreaterThan(String name, int age);

    // 조회
    // find...By (...에는 어떠한 변수명도 상관없다. 예) findHelloBy..), By뒤에 조회할 값을 적지 않으면 전체 조회

    // 주의 : 엔티티의 필드명이 변경되면 인터페이스에 정의한 메서드 이름도 함께 변경해야 한다.
}
