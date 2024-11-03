package com.choi76.web_socket_jwt.domain.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageDTO {
    private Long chatRoomId;
    private Long senderId;
    private String content;
}
