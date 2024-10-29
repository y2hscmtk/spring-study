package choi76.socket_chat.domain.chat.controller;

import choi76.socket_chat.domain.chat.service.ChatService;
import choi76.socket_chat.global.socket.handler.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final WebSocketChatHandler webSocketChatHandler;

    /**
     * 새로운 채팅방 생성
     */
    @PostMapping("/create")
    public ResponseEntity<?> createChatRoom(@RequestParam("ownerId") Long ownerId) {
        return chatService.createChatRoom(ownerId);
    }
}
