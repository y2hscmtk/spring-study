package choi76.socket_chat.global.socket.handler;

import choi76.socket_chat.domain.chat.entity.Chat;
import choi76.socket_chat.domain.chat.entity.ChatRoom;
import choi76.socket_chat.domain.chat.repository.ChatRepository;
import choi76.socket_chat.domain.chat.repository.ChatRoomRepository;
import choi76.socket_chat.domain.chat.service.ChatService;
import choi76.socket_chat.domain.member.repository.MemberRepository;
import choi76.socket_chat.global.socket.dto.ChatMessageDto;
import choi76.socket_chat.global.socket.dto.ChatMessageDto.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
 * WebSocket Handler 작성
 * 소켓 통신은 서버와 클라이언트가 1:n으로 관계를 맺으므로 한 서버에 여러 클라이언트 접속 가능
 * 서버에는 여러 클라이언트가 발송한 메세지를 받아 처리해줄 핸들러가 필요
 * TextWebSocketHandler를 상속받아 핸들러 작성
 * 클라이언트로 받은 메세지를 log로 출력하고 클라이언트로 새로운 메시지를 반환한다.
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatroomRepository;
    private final MemberRepository memberRepository;
    private final ChatService chatService;

    // 현재 연결된 세션들
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // 특정 채팅방에 소속된 세션 정보
    // key : chatRoomId ; Long
    // value : {session1, session2, ...} ; Set<WebSocketSession>
    private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        sessions.add(session); // 클라이언트 세션 정보 저장
    }

    // 소켓 통신 시 메세지의 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 소켓을 통해 전송된 message -> chatMessageDto로 변환
        String payload = message.getPayload();
        log.info("payload {}", payload);
        ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);

        Long chatRoomId = chatMessageDto.getChatRoomId();
        if (!chatRoomSessionMap.containsKey(chatRoomId)) {
            chatRoomSessionMap.put(chatRoomId, new HashSet<>());
        }
        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

        boolean isSuccess = true;
        // 입장의 경우
        if (chatMessageDto.getMessageType().equals(MessageType.ENTER)) {
            chatRoomSession.add(session);
            // 재입장 여부 확인 & 입장 성공 여부 확인
            isSuccess = chatService.addMemberToChatRoom(chatRoomId, chatMessageDto.getSenderId());
        } // 퇴장의 경우
        else if (chatMessageDto.getMessageType().equals(MessageType.EXIT)) {
            chatRoomSession.remove(session);
            sessions.remove(session);
            log.info("{} 퇴장", session.getId());
        }

        if (isSuccess) {
            ChatRoom chatRoom = chatService.findChatRoomWithMembers(chatRoomId);
            Chat newChat = Chat.builder()
                    .chatRoom(chatRoom)
                    .message(chatMessageDto.getMessage())
                    .build();
            chatService.saveChatMessage(newChat);
            sendMessageToChatRoom(chatMessageDto, chatRoomSession);
        }

    }

    // 소켓 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결 끊김 - Status: {}", session.getId(), status);
        sessions.remove(session);
    }

    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }


    // 채팅방에 새로운 채팅 메시지 전달
    // 채팅방에 소속된 클라이언트 세션에게 모두 새로운 채팅 전송
    private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDto));
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
