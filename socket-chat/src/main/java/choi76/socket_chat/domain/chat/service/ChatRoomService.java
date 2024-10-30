package choi76.socket_chat.domain.chat.service;

import choi76.socket_chat.domain.chat.dto.ChatResponseDTO;
import choi76.socket_chat.domain.chat.entity.Chat;
import choi76.socket_chat.domain.chat.entity.ChatRoom;
import choi76.socket_chat.domain.chat.repository.ChatRepository;
import choi76.socket_chat.domain.chat.repository.ChatRoomRepository;
import choi76.socket_chat.domain.member.entity.Member;
import choi76.socket_chat.domain.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    public ResponseEntity<?> createChatRoom(Long ownerId) {
        Member owner = memberRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found for id: " + ownerId));

        // 편의상 검증 로직 생략
        ChatRoom newChatRoom = ChatRoom.builder()
                .owner(owner)
                .title("테스트")
                .build();
        chatRoomRepository.save(newChatRoom);
        return ResponseEntity.ok("채팅방이 생성되었습니다 아이디 : " + newChatRoom.getId());
    }

    public ResponseEntity<?> getAllChats(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found for id: " + chatRoomId));
        List<Chat> chats = chatRoom.getChats();
        ArrayList<ChatResponseDTO> resultDto = new ArrayList<>();
        for (Chat chat : chats) {
            resultDto.add(ChatResponseDTO.builder()
                    .chatId(chat.getId())
                    .senderId(chat.getSender().getId())
                    .message(chat.getMessage())
                    .build());
        }
        return ResponseEntity.ok(resultDto);
    }

    public ChatRoom findChatRoomWithMembers(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found for id: " + chatRoomId));
        chatRoom.getChatRoomMembers().size(); // 강제 초기화
        return chatRoom;
    }

    public boolean addMemberToChatRoom(Long chatRoomId, Long memberId) {
        ChatRoom chatRoom = findChatRoomWithMembers(chatRoomId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found for id: " + memberId));
        return chatRoom.addMember(member);
    }

    public void saveChatMessage(Chat chat) {
        chatRepository.save(chat);
    }
}
