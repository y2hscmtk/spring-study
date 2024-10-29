package choi76.socket_chat.domain.chat.repository;

import choi76.socket_chat.domain.chat.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom,Long> {
}
