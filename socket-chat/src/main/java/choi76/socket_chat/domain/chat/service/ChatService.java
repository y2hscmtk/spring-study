package choi76.socket_chat.domain.chat.service;

import choi76.socket_chat.domain.chat.entity.Chatroom;
import choi76.socket_chat.domain.chat.repository.ChatRepository;
import choi76.socket_chat.domain.chat.repository.ChatroomRepository;
import choi76.socket_chat.domain.member.entity.Member;
import choi76.socket_chat.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatroomRepository chatroomRepository;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    public ResponseEntity<?> createChatRoom(Long ownerId) {
        Member owner = memberRepository.findById(ownerId).get();

        // 편의상 검증 로직 생략
        Chatroom newChatRoom = Chatroom.builder()
                .owner(owner)
                .title("테스트")
                .build();
        chatroomRepository.save(newChatRoom);
        return ResponseEntity.ok("채팅방이 생성되었습니다 아이디 : " + newChatRoom.getId());
    }
}
