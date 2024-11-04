package com.choi76.web_socket_jwt.domain.chat.controller;

import com.choi76.web_socket_jwt.domain.chat.dto.ChatMessageDTO;
import com.choi76.web_socket_jwt.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDTO messageDTO) {
        chatService.sendMessage(messageDTO);
    }

    @MessageMapping("/chat.enter")
    public void enterChatRoom(@Payload ChatMessageDTO messageDTO) {
        chatService.enterChatRoom(messageDTO.getChatRoomId(), messageDTO.getSenderId());
    }

    @MessageMapping("/chat.exit")
    public void exitChatRoom(@Payload ChatMessageDTO messageDTO) {
        chatService.exitChatRoom(messageDTO.getChatRoomId(), messageDTO.getSenderId());
    }
}

