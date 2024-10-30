package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Place;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceRequestDTO;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceResponseDTO;

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
}
