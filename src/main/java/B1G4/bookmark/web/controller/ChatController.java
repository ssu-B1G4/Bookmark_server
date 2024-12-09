package B1G4.bookmark.web.controller;


import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.security.handler.annotation.AuthUser;
import B1G4.bookmark.service.ChatRoomService.ChatRoomServiceImpl;
import B1G4.bookmark.web.dto.ChatMessageDTO.ChatMessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomServiceImpl chatRoomService;

    // 채팅방 입장
    /**
     * 채팅방 입장
     * 클라이언트는 /app/chat/{chatRoomId}/join으로 메시지를 발행합니다.
     * 서버는 /topic/chat/{chatRoomId}로 구독 중인 모든 클라이언트에게 메시지를 브로드캐스트합니다.
     */
    @Operation(
            summary = "채팅방 입장",
            description = "사용자가 특정 채팅방에 입장합니다. 입장 메시지는 구독 중인 모든 사용자에게 전송됩니다."
    )
    @MessageMapping("/chat/{chatRoomId}/join")
    @SendTo("/topic/chat/{chatRoomId}")
    public BaseResponse<ChatMessageDTO> joinChatRoom(
            @DestinationVariable @Parameter(
                    description = "입장할 채팅방의 ID",
                    required = true
            ) Long chatRoomId,
            @Parameter(name = "user", hidden = true)
            @AuthUser Member member) {
        ChatMessageDTO result = chatRoomService.handleJoinChatRoom(chatRoomId, member.getId());
        return BaseResponse.onSuccess(result);
    }

    // 메시지 전송
    /**
     * 메시지 전송
     * 클라이언트는 /app/chat/{chatRoomId}/send로 메시지를 발행합니다.
     * 서버는 /topic/chat/{chatRoomId}로 구독 중인 모든 클라이언트에게 메시지를 브로드캐스트합니다.
     */
    @Operation(
            summary = "메시지 전송",
            description = "사용자가 특정 채팅방에 메시지를 전송합니다. 메시지는 구독 중인 모든 사용자에게 브로드캐스트됩니다."
    )
    @MessageMapping("/chat/{chatRoomId}/send")
    @SendTo("/topic/chat/{chatRoomId}")
    public BaseResponse<ChatMessageDTO> sendMessage(
            @DestinationVariable @Parameter(
                    description = "메시지를 전송할 채팅방의 ID",
                    required = true,
                    example = "1"
            ) Long chatRoomId,
            @Parameter(
                    description = "전송할 메시지의 정보 (메시지만 포함)",
                    required = true
            )
            ChatMessageDTO messageDTO) {
        return BaseResponse.onSuccess(chatRoomService.handleSendMessage(chatRoomId, messageDTO));
    }
}