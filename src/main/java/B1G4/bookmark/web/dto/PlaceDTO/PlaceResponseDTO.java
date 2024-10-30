package B1G4.bookmark.web.dto.PlaceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PlaceResponseDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceIdDTO {
        Long placeId;
    }
}
