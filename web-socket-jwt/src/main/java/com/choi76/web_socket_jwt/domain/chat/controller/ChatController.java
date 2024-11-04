package com.choi76.web_socket_jwt.domain.chat.controller;

import com.choi76.web_socket_jwt.domain.chat.service.ChatRoomService;
import com.choi76.web_socket_jwt.domain.chat.service.ChatService;
import com.choi76.web_socket_jwt.global.jwt.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    @PostMapping("/enter/{chatRoomId}/")
    public void enterChatRoom(@PathVariable Long chatRoomId,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        chatService.enterChatRoom(chatRoomId, userDetails.getEmail());
    }

    @PostMapping("/exit/{chatRoomId}/")
    public void exitChatRoom(@PathVariable Long chatRoomId,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        chatService.exitChatRoom(chatRoomId, userDetails.getEmail());
    }

    /**
     * 모든 채팅방 정보 반환
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }
}

