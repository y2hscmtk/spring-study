package utilizingjpa.jpashop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import utilizingjpa.jpashop.domain.Address;
import utilizingjpa.jpashop.domain.Member;
import utilizingjpa.jpashop.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    // 회원 등록 기능
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { // BindingResult => 에러 발생시 처리
        if (result.hasErrors()) { // 에러 발생시
            return "members/createMemberForm"; // 현재 화면에 그대로 유지, 에러 내용만 전달
        }

        // 주소 정보 생성
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member); // 회원 가입 처리
        return "redirect:/"; // 처음 화면으로 넘어가기
    }
}
