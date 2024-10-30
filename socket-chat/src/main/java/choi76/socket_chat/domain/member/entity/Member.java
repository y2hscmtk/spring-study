package choi76.socket_chat.domain.member.entity;

import choi76.socket_chat.domain.chat.entity.ChatRoom;
import choi76.socket_chat.domain.chat.entity.ChatRoomMember;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String password;

    // 연관 관계 매핑
    @Builder.Default
    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL)
    private List<ChatRoom> myChatRoom = new ArrayList<>(); // 내가 생성한 채팅방

    @Builder.Default
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<ChatRoomMember> myParticipantChatRoom = new ArrayList<>(); // 내가 참여중인 채팅방
}
