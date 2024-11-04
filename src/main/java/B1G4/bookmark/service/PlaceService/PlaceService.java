package B1G4.bookmark.service.PlaceService;

import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceRequestDTO;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface PlaceService {
    Map<String, Double> getGeoData(String address);
    Place createPlace(PlaceRequestDTO.PlaceCreateDTO request, Double longitude, Double latitude);
    PlaceResponseDTO.PlacePreviewDTO previewPlace(Long placeId, Member member);
    PlaceResponseDTO.PlaceDetailDTO detailPlace(Long placeId, Member member);
    Page<Place> findNearbyPlaces(Double longitude, Double latitude, Double radius, Integer page);
    Page<Place> searchPlaces(String search, Integer page);
    Map<String, Map<String, String>> getOperatingTime(Place place);
    Boolean isOpen(Place place, String filterTime, String dayOfWeek);
    Page<Place> addFilter(Page<Place> places, String mood, String day, String time, String size, String outlet, String noise, String wifi);
    void bookmarkPlace(Member member, Long placeId);
}
