package com.choi76.web_socket_jwt.domain.chat.controller;

import com.choi76.web_socket_jwt.domain.chat.service.ChatRoomService;
import com.choi76.web_socket_jwt.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    @PostMapping("/enter/{chatRoomId}/{memberId}")
    public void enterChatRoom(@PathVariable Long chatRoomId, @PathVariable Long memberId) {
        chatService.enterChatRoom(chatRoomId, memberId);
    }

    @PostMapping("/exit/{chatRoomId}/{memberId}")
    public void exitChatRoom(@PathVariable Long chatRoomId, @PathVariable Long memberId) {
        chatService.exitChatRoom(chatRoomId, memberId);
    }

    /**
     * 모든 채팅방 정보 반환
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }
}

