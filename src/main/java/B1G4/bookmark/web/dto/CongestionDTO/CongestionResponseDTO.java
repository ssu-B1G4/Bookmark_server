package B1G4.bookmark.web.dto.CongestionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class CongestionResponseDTO {

    private Long placeId;
    private Map<String, Integer> congestionData;

}