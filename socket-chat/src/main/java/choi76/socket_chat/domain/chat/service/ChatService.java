package choi76.socket_chat.domain.chat.service;

import choi76.socket_chat.domain.chat.repository.ChatRepository;
import choi76.socket_chat.domain.chat.repository.ChatroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatService {
    private ChatroomRepository chatroomRepository;
    private ChatRepository chatRepository;
}
