package choi76.socket_chat.domain.member.controller;

import choi76.socket_chat.domain.member.dto.MemberJoinDto;
import choi76.socket_chat.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/member/")
public class MemberController {
    private MemberService memberService;

    @PostMapping("/join")
    private ResponseEntity<?> join(@RequestBody MemberJoinDto memberJoinDto) {
        return memberService.join(memberJoinDto);
    }
}
