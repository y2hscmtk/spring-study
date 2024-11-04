package com.choi76.web_socket_jwt.domain.chat.service;

import com.choi76.web_socket_jwt.domain.chat.dto.ChatMessageDTO;
import com.choi76.web_socket_jwt.domain.chat.entity.ChatMessage;
import com.choi76.web_socket_jwt.domain.chat.entity.ChatRoom;
import com.choi76.web_socket_jwt.domain.chat.entity.ChatRoomMember;
import com.choi76.web_socket_jwt.domain.chat.repository.ChatMessageRepository;
import com.choi76.web_socket_jwt.domain.chat.repository.ChatRoomRepository;
import com.choi76.web_socket_jwt.domain.chat.repository.ChatRoomMemberRepository;
import com.choi76.web_socket_jwt.domain.member.entity.Member;
import com.choi76.web_socket_jwt.domain.member.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void sendMessage(ChatMessageDTO messageDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(messageDTO.getChatRoomId())
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
        Member sender = memberRepository.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        ChatMessage chatMessage = ChatMessage.builder()
                .content(messageDTO.getContent())
                .timestamp(LocalDateTime.now())
                .chatRoom(chatRoom)
                .sender(sender)
                .build();

        chatMessageRepository.save(chatMessage);

        // topic/chatroom/{chatRoomId} 를 구독한 Client 들에게 새로운 데이터 전송
        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoom.getId(), messageDTO);
    }

    @Transactional
    public void enterChatRoom(Long chatRoomId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        Optional<ChatRoomMember> membership = chatRoomMemberRepository.findByChatRoomIdAndMemberId(
                chatRoomId, memberId);

        if (membership.isPresent()) {
            throw new RuntimeException("이미 채팅방에 소속된 회원입니다.");
        }

        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .member(member)
                .build();

        chatRoomMemberRepository.save(chatRoomMember);

        // 채팅방 입장 메시지 생성
        ChatMessage enterMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(member)
                .content(member.getEmail() + " 님이 입장하셨습니다.")
                .build();

        // 채팅방 반환용 메시지
        ChatMessageDTO message = ChatMessageDTO.builder()
                .chatRoomId(chatRoomId)
                .senderId(memberId)
                .content(member.getEmail() + " 님이 입장하셨습니다.")
                .build();

        chatMessageRepository.save(enterMessage);


        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId, message);
    }

    @Transactional
    public void exitChatRoom(Long chatRoomId, Long memberId) {
        ChatRoomMember chatRoomMember = chatRoomMemberRepository
                .findByChatRoomIdAndMemberId(chatRoomId, memberId)
                .orElseThrow(() -> new RuntimeException("Chat room member not found"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        chatRoomMemberRepository.delete(chatRoomMember);

        // 채팅방 퇴장 메시지 생성
        ChatMessage exitMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(member)
                .content(member.getEmail() + " 님이 퇴장 하셨습니다.")
                .build();

        // 채팅방 반환용 메시지
        ChatMessageDTO message = ChatMessageDTO.builder()
                .chatRoomId(chatRoomId)
                .senderId(memberId)
                .content(chatRoomMember.getMember().getEmail() + " 님이 퇴장하셨습니다.")
                .build();

        chatMessageRepository.save(exitMessage);

        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId, message);
    }
}
