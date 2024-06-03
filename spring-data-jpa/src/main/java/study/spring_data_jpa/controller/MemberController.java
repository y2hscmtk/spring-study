package study.spring_data_jpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.spring_data_jpa.entity.Member;
import study.spring_data_jpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("members/{id}")
    public String findMember(@PathVariable("id") Long id) {

        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // Web 확장 - 도메일 클래스 컨버터 사용
    @GetMapping("members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        // 스프링에 의해 pathVariable로 전달받은 아이디에 해당하는 회원을 자동으로 컨버팅하여 얻어온다.
        // HTTP 요청은 회원 id를 받지만, 도메인 클래스 컨버터가 중간에 동작하여 회원 엔티티를 반환한다.
        // 도메인 클래스 컨버터도 리파지토리를 사용하여 엔티티를 찾는다.
        // 권장하지 않음 => 복잡해지면 사용할 수 없다.
        // 주의 : 도메인 클래스 컨버터로 엔티티를 파라미터로 받으면, 이 엔티티는 단순 조회용으로 사용해야한다.
        // (트랜젝션이 없는 범위에서 엔티티를 조회하였으므로, 엔티티를 변경하여도 DB에 반영되지 않는다.)
        return member.getUsername();
    }

    // 테스트용 데이터 삽입
    @PostConstruct
    public void init() {
        memberRepository.save(new Member("AAA"));
    }
}
