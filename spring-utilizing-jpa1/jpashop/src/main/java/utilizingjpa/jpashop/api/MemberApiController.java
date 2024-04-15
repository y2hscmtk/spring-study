package utilizingjpa.jpashop.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import utilizingjpa.jpashop.domain.Member;
import utilizingjpa.jpashop.service.MemberService;

// @Controller + @ResponseBody = @RestController
@RestController // 데이터 자체를 json으로 보내겠다는 의미(스프링 MVC 강의 참고)
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원 Json데이터를 바디를 통해 받고 매핑
    // 장점 : 별도의 클래스(DTO)를 작성하지 않고 엔티티를 직접 받을 수 있다.
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) { // javax.validation
        Long id = memberService.join(member); // 회원 생성시 아이디를 반환함
        return new CreateMemberResponse(id);

        // 엔티티를 파라미터를 통해 받을 때의 문제점 => api 수정, 데이터베이스 수정 등의 이유로 엔티티 스펙이 변경될 수 있다.
        // => 파라미터를 통해 받을 경우 엔티티 스펙 변경 시 사이드 이펙트 발생 가능
        // => 따라서 가급적 api 개발시에는 파라미터로 entity를 받지 말아야 한다. + 외부에 노출해서는 안된다.
    }

    // 엔티티를 직접 받지 않고 별도의 Request클래스(DTO)를 사용하여 값을 받았다.
    // 장점 : 엔티티 스펙의 변화가 발생하더라도 API에 큰 영향이 없다. => 유지보수 용이
    @PostMapping("api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest requst) {
        Member member = new Member();
        member.setName(requst.name);
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }


    // 수정 요청
    // 대상 id는 pathVariable, 수정 내용은 body로
    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        // 정책 : 커멘드와 쿼리를 분리한다 => update된 멤버를 반환하는 것이 아닌, 다시 찾아서 조회 => 성능상 큰 이슈가 되지 않음
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest{ // DTO 요청 타입
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse { // DTO 반환 타입
        private Long id;
        private String name;
    }





    // V2에서 사용되기 위한 Request 클래스 => 엔티티 직접 노출과 파라미터 사용을 방지한다.
    // RequestDTO
    @Data
    static class CreateMemberRequest{
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

}
