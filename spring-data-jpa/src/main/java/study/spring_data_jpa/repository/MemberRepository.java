package study.spring_data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.spring_data_jpa.entity.Member;

// Repository 어노테이션을 적지 않아도 괜찮다.
public interface MemberRepository extends JpaRepository<Member,Long> {

}
