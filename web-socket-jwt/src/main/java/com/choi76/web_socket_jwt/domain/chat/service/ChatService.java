package com.choi76.web_socket_jwt.domain.chat.service;

import com.choi76.web_socket_jwt.domain.chat.entity.ChatRoom;
import com.choi76.web_socket_jwt.domain.chat.entity.ChatRoomMember;
import com.choi76.web_socket_jwt.domain.chat.repository.ChatRoomRepository;
import com.choi76.web_socket_jwt.domain.chat.repository.ChatRoomMemberRepository;
import com.choi76.web_socket_jwt.domain.member.entity.Member;
import com.choi76.web_socket_jwt.domain.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void enterChatRoom(Long chatRoomId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        // 중복 회원 방지 로직 작성 필요
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

        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId,
                member.getEmail() + " has entered the chat.");
    }

    @Transactional
    public void exitChatRoom(Long chatRoomId, Long memberId) {
        ChatRoomMember chatRoomMember = chatRoomMemberRepository
                .findByChatRoomIdAndMemberId(chatRoomId, memberId)
                .orElseThrow(() -> new RuntimeException("Chat room member not found"));

        chatRoomMemberRepository.delete(chatRoomMember);

        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId,
                chatRoomMember.getMember().getEmail() + " has left the chat.");
    }
}
