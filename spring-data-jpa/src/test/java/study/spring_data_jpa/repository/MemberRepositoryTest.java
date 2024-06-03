package study.spring_data_jpa.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.spring_data_jpa.dto.MemberDto;
import study.spring_data_jpa.entity.Member;
import study.spring_data_jpa.entity.Team;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Optional<Member> optionalMember =
                memberRepository.findById(savedMember.getId());
        Member findMember = optionalMember.get();

        assertThat(findMember.getId()).isEqualTo(member.getId()); // option + Enter + static import
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        assertThat(findMember).isEqualTo(member); // findMember == member
    }

    // SPRING DATA JPA 검증
    // 구현체를 바탕으로 생성된 코드도 기능적으로 동일하게 동작한다.
    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member m1 = new Member("aaa", 10);
        Member m2 = new Member("aaa", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("aaa", 15);

        // 성공적으로 조회되었다면 m2를 리턴해야함
        assertThat(result.get(0).getUsername()).isEqualTo("aaa");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testNamedQuery() {
        Member m1 = new Member("aaa", 10);
        Member m2 = new Member("bbb", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("aaa");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }

    @Test
    public void testQuery() {
        Member m1 = new Member("aaa", 10);
        Member m2 = new Member("bbb", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("aaa", 10);
        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    public void findUsernameList() {
        Member m1 = new Member("aaa", 10);
        Member m2 = new Member("bbb", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void findMemberDto() {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA", 10);
        m1.changeTeam(team); // 연관관계 편의 메소드 사용
        memberRepository.save(m1);

        List<MemberDto> result = memberRepository.findMemberDto();
        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }


    @Test
    public void findByNames() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void returnType() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // 반환 타입을 다르게 하여 편하게 사용 가능
        // 1. 존재하지 않을 경우 NULL
        // 2. 단건 조회시 결과물이 2개 이상인 경우, 예외가 발생한다. NonUniqueResultException
        Member findMember = memberRepository.findMemberByUsername("AAA");
        System.out.println("findMember = " + findMember);

        // 주의 : 반환 결과가 없는 경우, NULL이 아닌 EMPTY를 반환한다.
        // 컬렉션을 반환받는 경우, 빈 컬렉션을 반환받는다.
        // 결론 : 자바8 이상 환경에서 가급적 Optional을 사용하도록 한다.
    }


    @Test
    public void paging() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        // 0 페이지에서부터 3개 가져온다, 정렬 조건은 Sort.by로 설정
        int age = 10;
        PageRequest pageRequest =
                PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        // 페이징 결과 DTO로 변환 => MAP를 활용하여 유용하게 사용 가능
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), member.getUsername()));

        // then
        List<Member> content = page.getContent(); // 실제 내용 가져오기 3개
        long totalElements = page.getTotalElements(); // 총 몇개인지 얻어오기 5개

        for (Member member : content) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElements = " + totalElements);

        assertThat(content.size()).isEqualTo(3); // 현재 페이지에서 얻어온 row의 수
        assertThat(page.getTotalElements()).isEqualTo(5); // 모든 row의 개수
        assertThat(page.getNumber()).isEqualTo(0); // 현재 페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(2); // 총 페이지 수 3개씩 가져오므로 2개의 페이지가 나오게됨
        assertThat(page.isFirst()).isTrue(); // 현재 페이지가 첫 페이지인지 True, False
        assertThat(page.hasNext()).isTrue(); // 다음 페이지가 있는지 True, False

    }


    @Test
    public void bulkUpdate() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        // when
        int resultCount = memberRepository.bulkAgePlus(20); // 변경된 수 반환
        entityManager.flush(); // 즉시 반영 (현재까지 쌓인 쿼리를 모두 반영한다. => 클리어 이전에 수행)
        entityManager.clear(); // 벌크 연산 이후에는 영속성 컨텍스트를 초기화한다.(벌크 연산은 영속성 컨텍스트의 엔티티를 무시하고 직접적으로 쿼리를 날리므로)

        // 벌크연산은 영속성 컨텍스트를 변환시키지 않는다.
        // => 즉 member5 객체에서의 나이는 여전히 40이다.(벌크 연산에 의해 41이 되어야 함에도 불구하고)
        List<Member> result = memberRepository.findByUsername("member5");
        Member member5 = result.get(0); // Member(id = 5, username = "member5", agg = 40)
        // 벌크연산은 영속성 컨텍스트를 무시하고 DB에 직접적으로 쿼리를 수행하며, 영속성 컨텍스트는 변경사항을 알지못함
        // 따라서 벌크연산 이후에는 영속성 컨텍스트를 모두 날리는 것이 좋다.

        // then
        assertThat(resultCount).isEqualTo(3); // 20, 21, 40
    }

    @Test
    public void findMemberLazy() {
        // given
        // member1 -> TeamA
        // member2 -> TeamB
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        entityManager.flush();
        entityManager.clear();

        // when
        // 우선 member만 가져온다.(team은 지연로딩)
        // EntityManager를 사용하면 편하게 패치 조인을 할 수 있다.
        List<Member> members = memberRepository.findAll();

        // member와 연관관계를 맺고 있는 Team은 LAZY로 지연로딩 되어있기 때문에 N+1문제가 발생한다.
        // Member 조회 + Member와 연관된 팀 조회
        // 패치조인을 하게되면 조인 후, Select 절에 우리가 원하는 쿼리를 삽입 할 수 있다.
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            // Team의 name에 접근하기 위해 Team을 조회하는 쿼리가 한번 씩 더 나가게 된다.
            // (member 조회시, team은 지연로딩되기 때문 => team의 실제 값에 접근하려는 순간 쿼리가 나간다.)
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
    }

    @Test
    public void queryHint() {
        // given
        Member member1 = memberRepository.save(new Member("member1", 10));
        entityManager.flush(); // 영속성 컨텍스트 동기화
        entityManager.clear(); // 영속성 컨텍스트 초기화

        // when
        // ReadOnly설정이 true이기 때문에, 내부적으로 변경이 되지 않는다고 가정하고, 스냅샷을 만들지 않는다.
        // => 메모리 관리에 용이하다.
        // 조회 전용인 경우, 수정이 발생할 필요가 없기 때문
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2"); // // 영속화된 엔티티 변경사항 발생

        entityManager.flush(); // => 더티채킹에 의해 update 쿼리 생성
    }

    @Test
    public void lock() {
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        entityManager.flush();
        entityManager.clear();

        // JPA가 제공하는 Lock을 편리하게 사용할 수 있다.
        // 실시간 트래픽이 많은 서비스에서는 가급적이면 사용하지 말아야 한다.
        List<Member> result
                = memberRepository.findLockByUsername("member1");
    }
}
