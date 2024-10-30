package choi76.socket_chat.domain.chat.repository;

import choi76.socket_chat.domain.chat.entity.ChatRoom;
import choi76.socket_chat.domain.chat.entity.ChatRoomMember;
import choi76.socket_chat.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember,Long> {
    Optional<ChatRoomMember> findByChatRoomAndMember(ChatRoom chatRoom, Member member);
}
