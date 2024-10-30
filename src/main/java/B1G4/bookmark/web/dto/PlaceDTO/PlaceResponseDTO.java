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
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceDetailDTO {
        Long placeId;
        String profileImgUrl; // 대표 이미지 url
        String name;
        String address;
        Boolean isSaved;
        String category;
        String outlet;
        String size;
        String wifi;
        String noise;
        String mood1;
        String mood2;
        Double longitude;
        Double latitude;
        String phone;
        String time;
        String url;
    }
}
