package choi76.socket_chat.domain.chat.repository;

import choi76.socket_chat.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
}
