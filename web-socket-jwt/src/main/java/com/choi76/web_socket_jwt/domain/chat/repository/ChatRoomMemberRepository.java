package com.choi76.web_socket_jwt.domain.chat.repository;

import com.choi76.web_socket_jwt.domain.chat.entity.ChatRoomMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    Optional<ChatRoomMember> findByChatRoomIdAndMemberId(Long chatRoomId, Long memberId);
}
