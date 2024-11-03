package com.choi76.web_socket_jwt.domain.chat.controller;

import com.choi76.web_socket_jwt.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/enter/{chatRoomId}/{memberId}")
    public void enterChatRoom(@PathVariable Long chatRoomId, @PathVariable Long memberId) {
        chatService.enterChatRoom(chatRoomId, memberId);
    }

    @PostMapping("/exit/{chatRoomId}/{memberId}")
    public void exitChatRoom(@PathVariable Long chatRoomId, @PathVariable Long memberId) {
        chatService.exitChatRoom(chatRoomId, memberId);
    }
}

