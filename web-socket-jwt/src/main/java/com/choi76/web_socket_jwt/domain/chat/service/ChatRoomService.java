package com.choi76.web_socket_jwt.domain.chat.service;

import com.choi76.web_socket_jwt.domain.chat.entity.ChatRoom;
import com.choi76.web_socket_jwt.domain.chat.entity.ChatRoomDto;
import com.choi76.web_socket_jwt.domain.chat.repository.ChatRoomRepository;
import com.choi76.web_socket_jwt.global.response.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    public ResponseEntity<?> getAllChatRooms() {
        List<ChatRoom> allChatRooms = chatRoomRepository.findAll();
        ArrayList<ChatRoomDto> resultDto = new ArrayList<>();
        for (ChatRoom allChatRoom : allChatRooms) {
            resultDto.add(ChatRoomDto.builder()
                    .chatRoomId(allChatRoom.getId())
                    .chatRoomName(allChatRoom.getName())
                    .build());
        }
        return ResponseEntity.ok(ApiResponse.onSuccess(resultDto));
    }
}
