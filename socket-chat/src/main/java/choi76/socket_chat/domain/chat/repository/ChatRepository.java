package choi76.socket_chat.domain.chat.repository;

import choi76.socket_chat.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long> {
}
