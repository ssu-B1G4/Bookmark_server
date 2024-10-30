package B1G4.bookmark.web.dto.PlaceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PlaceResponseDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceIdDTO {
        Long placeId;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlacePreviewDTO {
        Long placeId;
        String name;
        String size;
        String outlet;
        String wifi;
        Boolean isSaved;
        String mood1;
        String mood2;
        //TODO: 이미지 추가 후 추가
        List<String> imgUrls;
    }
}
