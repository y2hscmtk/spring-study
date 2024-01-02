package com.example.springintroduction.controller;

import com.example.springintroduction.domain.Member;
import com.example.springintroduction.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    // 스프링이 실행되면,
    // 스프링 컨테이너가 @Controller가 붙어있는 객체를 생성하여 관리한다 => '스프링 빈'이 관리된다.(싱글톤 객체)
    // 하나만 생성되고 여러번 생성할 필요없는 클래스들은 스프링 컨테이너에 스프링 빈으로 등록하여 사용하는것이 좋다.

    // <스프링 빈 의존관계 설정 방법>
    // MemberController -> MemberService
    private final MemberService memberService;
    // 위와 같은 의존관계를 갖는 상황에서 미리 생성되어 스프링 컨테이너에 존재하는 MemberService를 연결해줘야함
    // @AutoWired annotation을 사용하여 스프링 컨테이너의 스프링 빈 객체를 자동으로 연결해줄 수 있다.
    // => 의존관계 주입(Dependency Injection)
    @Autowired // 스프링 컨테이너에서 memberService를 가져온다. (memberService또한 스프링 빈으로 등록되어있어야한다.)
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 의존관계 주입 방법

    // 1. 필드 주입
    // @Autowired private final MemberService memberService;
    // 2. 생성자 주입
//    @Autowired // 스프링 컨테이너에서 memberService를 가져온다. (memberService또한 스프링 빈으로 등록되어있어야한다.)
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }
    // 3. Setter 주입
//    @Autowired
//    public void setMemberService(MemberService memberService){
//        this.memberService = memberService;
//    }
    // => 기본적으로 런타임에서 변경될 일은 거의 없으므로, 생성자 주입을 권장한다.

    // 회원 등록
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    // 데이터 전달 받기
    // MemberForm의 name 변수에 값이 삽입됨
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/"; // 시작화면으로 되돌리기
    }

    // 회원 조회
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        // members 라는 키 값으로 뷰 탬플릿에 데이터 전달
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
