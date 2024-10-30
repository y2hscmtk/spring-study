package choi76.socket_chat.domain.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponseDTO {
    private Long chatId;
    private Long senderId;
    private String message;
}
