package choi76.socket_chat.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberJoinDto {
    private String username;
    private String password;
}

