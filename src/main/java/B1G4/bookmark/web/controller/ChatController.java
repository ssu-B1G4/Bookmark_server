package B1G4.bookmark.web.controller;


import B1G4.bookmark.domain.Member;
import B1G4.bookmark.security.handler.annotation.AuthUser;
import B1G4.bookmark.service.ChatRoomService.ChatRoomServiceImpl;
import B1G4.bookmark.web.dto.ChatMessageDTO.ChatMessageDTO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomServiceImpl chatRoomService;

    // 채팅방 입장
    @MessageMapping("/chat/{chatRoomId}/join")
    @SendTo("/topic/chat/{chatRoomId}")
    public ChatMessageDTO joinChatRoom(
            @DestinationVariable Long chatRoomId,
            @Parameter(name = "user", hidden = true)
            @AuthUser Member member) {
        return chatRoomService.handleJoinChatRoom(chatRoomId, member.getId());
    }

    // 메시지 전송
    @MessageMapping("/chat/{chatRoomId}/send")
    @SendTo("/topic/chat/{chatRoomId}")
    public ChatMessageDTO sendMessage(
            @DestinationVariable Long chatRoomId,
            ChatMessageDTO messageDTO) {
        return chatRoomService.handleSendMessage(chatRoomId, messageDTO);
    }
}