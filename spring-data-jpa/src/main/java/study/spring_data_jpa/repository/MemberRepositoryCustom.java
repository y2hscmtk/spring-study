package study.spring_data_jpa.repository;

import study.spring_data_jpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    // Spring Data JPA가 구현하는 내용이 아닌, 내가 구현한 내용을 사용하고 싶을때
    List<Member> findMemberCustom();
}
