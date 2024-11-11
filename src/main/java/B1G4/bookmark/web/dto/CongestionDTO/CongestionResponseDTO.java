package B1G4.bookmark.web.dto.CongestionDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CongestionResponseDTO {

    private List<HourValueDTO> congestionData;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class HourValueDTO {
        private String hour;
        private Integer value;
    }
}