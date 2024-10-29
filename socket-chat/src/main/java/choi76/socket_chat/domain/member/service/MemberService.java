package choi76.socket_chat.domain.member.service;

import choi76.socket_chat.domain.member.dto.MemberJoinDto;
import choi76.socket_chat.domain.member.entity.Member;
import choi76.socket_chat.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public ResponseEntity<?> join(MemberJoinDto memberJoinDto) {
        // 예외처리 코드는 편의상 생략
        Member newMember = Member.builder()
                .username(memberJoinDto.getUsername())
                .password(memberJoinDto.getPassword())
                .build();
        memberRepository.save(newMember);

        return ResponseEntity.ok("회원가입 성공");
    }
}
