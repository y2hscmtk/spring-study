package choi76.socket_chat.domain.chat.controller;

import choi76.socket_chat.domain.chat.service.ChatService;
import choi76.socket_chat.global.socket.handler.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final WebSocketChatHandler webSocketChatHandler;
}
