package B1G4.bookmark.service.ChatRoomService;

import B1G4.bookmark.domain.ChatRoom;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.repository.ChatRoomRepository;
import B1G4.bookmark.repository.MemberRepository;
import B1G4.bookmark.web.dto.ChatMessageDTO.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl {


    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    // 채팅방 입장 처리
    public ChatMessageDTO handleJoinChatRoom(Long chatRoomId, Long senderId) {
        // 채팅방 유효성 확인
        chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));

        // 사용자 확인 및 닉네임 설정
        Member member = memberRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        String nickname = member.getNickname();
        String message = nickname + " 님이 입장했습니다.";
        LocalDateTime timestamp = LocalDateTime.now();

        return new ChatMessageDTO(nickname, message, timestamp);
    }

    // 메시지 전송 처리
    public ChatMessageDTO handleSendMessage(Long chatRoomId, ChatMessageDTO messageDTO) {
        // 채팅방 유효성 확인
        chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));

        // 메시지에 타임스탬프 추가
        messageDTO.setTimestamp(LocalDateTime.now());

        return messageDTO;
    }
}