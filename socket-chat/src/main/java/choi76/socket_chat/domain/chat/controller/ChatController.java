package choi76.socket_chat.domain.chat.controller;

import choi76.socket_chat.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatRoomService chatRoomService;

    /**
     * 새로운 채팅방 생성
     */
    @PostMapping("/create")
    public ResponseEntity<?> createChatRoom(@RequestParam("ownerId") Long ownerId) {
        return chatRoomService.createChatRoom(ownerId);
    }

    /**
     * 채팅 목록 가져오기
     */
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<?> getAllChats(@PathVariable("chatRoomId") Long chatRoomId) {
        return chatRoomService.getAllChats(chatRoomId);
    }
}
