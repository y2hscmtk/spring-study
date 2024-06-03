package study.spring_data_jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.spring_data_jpa.dto.MemberDto;
import study.spring_data_jpa.entity.Member;

import java.util.List;
import java.util.Optional;

/**
 * 자세한 문법은 Spring.io - Projects - Spring Data - Reference Doc 참고
 * https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
 * 모든 기능이 다 되는 것은 아니다. => 필요한 기능이 있다면 별도로 만들어야한다.
 * 조회해야 하는 요소가 2개 이상이라면(매개변수가 2개 이상이라면) 쿼리를 사용하는 것을 추천한다.
 * */

// Repository 어노테이션을 적지 않아도 괜찮다.
public interface MemberRepository extends JpaRepository<Member,Long> {
    // 쿼리 메소드
    //List<Member> findByUsername(String username); // 별도로 구현하지 않아도 Spring Data JPA에 의해 구현된다.

    // name과 이름이 일치하면서, 나이가 age이상인 회원 조회
    List<Member> findByUsernameAndAgeGreaterThan(String name, int age);

    // 조회
    // find...By (...에는 어떠한 변수명도 상관없다. 예) findHelloBy..), By뒤에 조회할 값을 적지 않으면 전체 조회

    // 주의 : 엔티티의 필드명이 변경되면 인터페이스에 정의한 메서드 이름도 함께 변경해야 한다.

    //@Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);
    // 관례상 네임드 쿼리가 존재하는지 찾고, 없다면 새롭게 생성한다. 따라서 @Query가 없더라도 먼저 네임드 쿼리가 존재하는지 찾는다.

    // 장점 1. 복잡한 쿼리를 사용할때 유용하다.(메소드 이름이 길어지지 않아도 된다.)
    // 장점 2. 개발 단계에서 오류를 찾기 편하다.(엔티티 이름을 잘못 설정한 경우, 오류를 찾기 용이하다.)
    // 쿼리가 너무 복잡하다면 @Query를 사용하는 것을 추천한다.
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // DTO로 결과물을 조회하고 싶은 경우
    @Query("select new study.spring_data_jpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // Collection을 활용한 조회
    // 매개변수로 전달받은 리스트 names에 존재하는 이름이 있는지 in을 사용하여 검색
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    // find..By 문 (..안에 들어갈 이름은 자유롭게 설정 가능)
    // 스프링 데이터 JPA는 반환 타입을 자유롭게 지원해준다.
    List<Member> findListByUsername(String username); // 컬렉션 반환
    Member findMemberByUsername(String username); // 단건 반환
    Optional<Member> findOptionalByUsername(String username); // 단건 Optional 반환

    // 스프링 데이터 JPA 페이징과 정렬
    Page<Member> findByAge(int age, Pageable pageable); // Pageable에는 반환타입에 대한 조건 설정, 반환타입은 Page

    // DATA JPA에서의 벌크성 쿼리
    // 벌크연산은 영속성 컨텍스트를 무시하고 연산을 수행한다. => Modifying에 clear옵션을 주면 영속성 컨텍스트를 비워준다.
    // 수정 쿼리의 경우 Modifying을 적어줘야한다.
    @Modifying(clearAutomatically = true) // 쿼리 수행 이후 영속성 컨텍스트를 비운다.(em.clear() 수행)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
}
