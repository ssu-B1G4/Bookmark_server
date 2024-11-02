package B1G4.bookmark.web.dto.PlaceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
        List<String> moods;
        List<String> placeImgList;
        Integer reviewCount;
        Double longitude;
        Double latitude;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlacePreviewListDTO {
        List<PlacePreviewDTO> placePreviewDTOList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;

    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceDetailDTO {
        Long placeId;
        List<String> placeImgList;
        String name;
        String address;
        Boolean isSaved;
        String category;
        String outlet;
        String size;
        String wifi;
        String noise;
        List<String> moods;
        Double longitude;
        Double latitude;
        String phone;
        String url;
        Map<String, Map<String, String>> operatingTimeList;
    }
}
