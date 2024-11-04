package com.choi76.web_socket_jwt.domain.chat.service;

import com.choi76.web_socket_jwt.domain.chat.dto.ChatMessageRequestDTO;
import com.choi76.web_socket_jwt.domain.chat.dto.ChatMessageResponseDTO;
import com.choi76.web_socket_jwt.domain.chat.entity.ChatMessage;
import com.choi76.web_socket_jwt.domain.chat.entity.ChatRoom;
import com.choi76.web_socket_jwt.domain.chat.entity.ChatRoomMember;
import com.choi76.web_socket_jwt.domain.chat.repository.ChatMessageRepository;
import com.choi76.web_socket_jwt.domain.chat.repository.ChatRoomRepository;
import com.choi76.web_socket_jwt.domain.chat.repository.ChatRoomMemberRepository;
import com.choi76.web_socket_jwt.domain.member.entity.Member;
import com.choi76.web_socket_jwt.domain.member.repository.MemberRepository;
import com.choi76.web_socket_jwt.global.enums.statuscode.ErrorStatus;
import com.choi76.web_socket_jwt.global.exception.GeneralException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void sendMessage(ChatMessageRequestDTO messageDTO, String email) {
        ChatRoom chatRoom = findChatRoomById(messageDTO.getChatRoomId());
        Member sender = findMemberByEmail(email);

        // 저장용 메시지 생성
        ChatMessage chatMessage = toChatMessage(messageDTO, chatRoom, sender);
        chatMessageRepository.save(chatMessage);

        // 반환용 메시지 생성
        ChatMessageResponseDTO responseDto = toResponseDto(messageDTO.getContent(), sender);

        // topic/chatroom/{chatRoomId} 를 구독한 Client 들에게 새로운 데이터 전송
        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoom.getId(), responseDto);
    }

    @Transactional
    public void enterChatRoom(Long chatRoomId, String email) {
        ChatRoom chatRoom = findChatRoomById(chatRoomId);
        Member sender = findMemberByEmail(email);

        Optional<ChatRoomMember> membership =
                chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, sender);

        if (membership.isPresent()) {
            throw new RuntimeException("이미 채팅방에 소속된 회원입니다.");
        }

        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .member(sender)
                .build();

        chatRoomMemberRepository.save(chatRoomMember);

        // 채팅방 입장 메시지 생성
        ChatMessage enterMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(sender.getEmail() + " 님이 입장하셨습니다.")
                .build();

        // 채팅방 반환용 메시지
        ChatMessageResponseDTO message = ChatMessageResponseDTO.builder()
                .email(email)
                .content(email + " 님이 입장하셨습니다.")
                .build();

        chatMessageRepository.save(enterMessage);

        log.info(chatRoomId + " room enter : " , message.getContent());
        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId, message);
    }

    @Transactional
    public void exitChatRoom(Long chatRoomId, String email) {
        Member sender = findMemberByEmail(email);
        ChatRoom chatRoom = findChatRoomById(chatRoomId);

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(chatRoom,sender)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHAT_ROOM_MEMBER_NOT_FOUND));

        chatRoomMemberRepository.delete(chatRoomMember);

        // 채팅방 퇴장 메시지 생성
        ChatMessage exitMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(email + " 님이 퇴장 하셨습니다.")
                .build();

        // 채팅방 반환용 메시지
        ChatMessageResponseDTO message = ChatMessageResponseDTO.builder()
                .email(email)
                .content(chatRoomMember.getMember().getEmail() + " 님이 퇴장하셨습니다.")
                .build();

        chatMessageRepository.save(exitMessage);
        log.info(chatRoomId + " room exit : " , message.getContent());
        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId, message);
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email)
                         .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    public ChatRoom findChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHAT_ROOM_NOT_FOUND));
    }

    public ChatMessage toChatMessage(ChatMessageRequestDTO dto, ChatRoom chatRoom, Member sender) {
        return ChatMessage.builder()
                .content(dto.getContent())
                .timestamp(LocalDateTime.now())
                .chatRoom(chatRoom)
                .sender(sender)
                .build();
    }

    public ChatMessageResponseDTO toResponseDto(String message, Member sender) {
        return ChatMessageResponseDTO.builder()
                .email(sender.getEmail())
                .content(message)
                .build();
    }
}
