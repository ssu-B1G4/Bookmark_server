package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Place;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceRequestDTO;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceResponseDTO;

import java.util.List;

public class PlaceConverter {
    public static Place toPlace(PlaceRequestDTO.PlaceCreateDTO request, Double longitude, Double latitude) {
        return Place.builder()
                .address(request.getAddress())
                .name(request.getName())
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }
    public static PlaceResponseDTO.PlaceIdDTO toPlaceIdDTO(Place place) {
        return PlaceResponseDTO.PlaceIdDTO.builder()
                .placeId(place.getId())
                .build();
    }

    public static PlaceResponseDTO.PlacePreviewDTO toPlacePreviewDTO(Place place, Boolean isSaved, List<String> placeImgList) {
        return PlaceResponseDTO.PlacePreviewDTO.builder()
                .placeId(place.getId())
                .isSaved(isSaved)
                .mood1(place.getMood1().getViewName())
                .mood2(place.getMood2().getViewName())
                .size(place.getSize().getViewName())
                .outlet(place.getOutlet().getViewName())
                .name(place.getName())
                .wifi(place.getWifi().getViewName())
                .placeImgList(placeImgList)
                .reviewCount(place.getReviewCount())
                .longitude(place.getLongitude())
                .latitude(place.getLatitude())
                .build();
    }
    public static PlaceResponseDTO.PlaceDetailDTO toPlaceDetailDTO(Place place, Boolean isSaved, List<String> placeImgList) {
        return PlaceResponseDTO.PlaceDetailDTO.builder()
                .placeId(place.getId())
                .size(place.getSize().getViewName())
                .wifi(place.getWifi().getViewName())
                .mood1(place.getMood1().getViewName())
                .mood2(place.getMood2().getViewName())
                .url(place.getUrl())
                .phone(place.getPhone())
                .name(place.getName())
                .noise(place.getNoise().getViewName())
                .placeImgList(placeImgList)
                .time(place.getTime())
                .category(place.getCategory().getViewName())
                .longitude(place.getLongitude())
                .latitude(place.getLatitude())
                .phone(place.getPhone())
                .isSaved(isSaved)
                .address(place.getAddress())
                .outlet(place.getOutlet().getViewName())
                .build();
    }
}
