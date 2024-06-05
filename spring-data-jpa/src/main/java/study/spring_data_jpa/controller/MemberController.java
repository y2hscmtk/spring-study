package study.spring_data_jpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.spring_data_jpa.dto.MemberDto;
import study.spring_data_jpa.entity.Member;
import study.spring_data_jpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    // 컨버팅을 사용하기 위해선, Controller 코드 내부에 Repository를 빈으로 갖고 있어야 한다.
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

    @GetMapping("members")
    // 필요하다면 @PageableDefault를 통해 기본 값 설정이 가능하다.
    public Page<MemberDto> list(@PageableDefault(size = 5,sort = "username") Pageable pageable) {
        // 어떠한 Spring Data JPA 쿼리일지라도 pageable을 넘기면 된다.
        // api 요청시에는 평범한 GET 매핑으로 localhost:8080/members를 요청하면 된다.
        // 추가적으로 페이징 쿼리가 필요할 경우 파라미터로 page,size,sort등을 요청할 수 있다.
        // http 요청이 들어올 때 스프링부트에 의해 Pageable 인터페이스가 구현되어 사용된다.
        Page<Member> page = memberRepository.findAll(pageable);// pagingSortingRepository ~
        // 반환타입이 Page<Member>이기 때문에 엔티티 스펙이 그대로 노출된다. => DTO 변환을 하는 것이 좋다.
        Page<MemberDto> map = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));

        return map;

//        return memberRepository.findAll(pageable)
//                .map(MemberDto::new);

        // Pageable pageable을 사용하지 않고 PageRequest.of를 선언하는 방법도 있다.
    }


    //테스트용 데이터 삽입
    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i,i));
        }
    }
}
