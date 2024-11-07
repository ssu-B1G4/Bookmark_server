package B1G4.bookmark.web.dto.CongestionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class CongestionStatusDTO {
    private Long placeId;
    private String time;
    private String status; // 혼잡도 상태: 여유, 보통, 혼잡
}