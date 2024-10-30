package choi76.socket_chat.domain.chat.entity;

import choi76.socket_chat.domain.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;
    private String title; // 채팅방 이름

    // 연관 관계 매핑
    @Builder.Default
    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    // 채팅방에 소속된 회원들
    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatRoomMember> chatRoomMembers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Member owner; // 채팅방 방장

    // === 편의 메소드 ====
    public void addChat(Chat chat) {
        this.chats.add(chat);
        if (chat.getChatRoom() != this) {
            chat.setChatRoom(this);
        }
    }

    public boolean addMember(Member member) {
        // 중복 참여 방지 : 같은 아이디의 회원이 존재하는지 확인
        boolean alreadyExists = chatRoomMembers.stream()
                .anyMatch(crm -> Objects.equals(crm.getMember().getId(), member.getId()));
        if (!alreadyExists) {
            ChatRoomMember newMember = ChatRoomMember.builder()
                    .chatRoom(this)
                    .member(member)
                    .build();
            this.chatRoomMembers.add(newMember);
            return true;
        }
        return false;
    }

    // 채팅방 멤버에서 제거
    public void removeMember(ChatRoomMember chatRoomMember) {
        this.chatRoomMembers.remove(chatRoomMember);
    }


}
