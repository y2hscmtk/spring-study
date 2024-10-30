package choi76.socket_chat.domain.chat.service;

import choi76.socket_chat.domain.chat.entity.ChatRoom;
import choi76.socket_chat.domain.chat.entity.ChatRoomMember;
import choi76.socket_chat.domain.chat.repository.ChatRoomMemberRepository;
import choi76.socket_chat.domain.chat.repository.ChatRoomRepository;
import choi76.socket_chat.domain.member.entity.Member;
import choi76.socket_chat.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomMemberService {
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    // 채팅방 회원 제거
    public void removeMemberFromChatRoom(Long chatRoomId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found for id: " + memberId));

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found for id: " + chatRoomId));

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, member)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoomMember not found for id: " + chatRoomId));

        // 회원 제거
        chatRoom.removeMember(chatRoomMember);
        chatRoomMemberRepository.delete(chatRoomMember);
    }

}
