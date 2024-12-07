package B1G4.bookmark.web.dto.ChatMessageDTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private String nickname;          // 보낸 사용자 닉네임
    private String message;           // 메시지 내용
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timestamp;  // 메시지 타임스탬프
}