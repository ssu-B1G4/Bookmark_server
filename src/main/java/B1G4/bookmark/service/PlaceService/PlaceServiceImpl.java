package B1G4.bookmark.service.PlaceService;

import B1G4.bookmark.converter.PlaceConverter;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.repository.PlaceImgRepository;
import B1G4.bookmark.repository.PlaceRepository;
import B1G4.bookmark.service.MemberService.MemberServiceImpl;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceRequestDTO;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService{
    @Value("${NAVER_MAP_URL}")
    private String apiUrl;
    @Value("${NAVER_CLIENT_ID}")
    private String clientId;
    @Value("${NAVER_CLIENT_SECRET}")
    private String clientSecret;

    private final PlaceRepository placeRepository;
    private final MemberServiceImpl memberService;
    private final PlaceImgRepository placeImgRepository;
    private static final Double EARTH_RADIUS = 6371.0;

    @Override
    public Map<String, Double> getGeoData(String address) {
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
            headers.set("X-NCP-APIGW-API-KEY", clientSecret);
            headers.set("ACCEPT", "application/json");
            String fullUrl = apiUrl + "?query=" + address;
            HttpEntity<String> http = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.GET,
                    http,
                    Map.class
            );
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Double> result = new HashMap<>();
                Map<String, Object> responseBody = response.getBody();

                List<Map<String, Object>> addresses = (List<Map<String, Object>>) responseBody.get("addresses");
                if (!addresses.isEmpty()) {
                    Map<String, Object> firstAddress = addresses.get(0);
                    // Convert String coordinates to Double
                    String xStr = String.valueOf(firstAddress.get("x"));
                    String yStr = String.valueOf(firstAddress.get("y"));

                    try {
                        result.put("longitude", Double.parseDouble(xStr));
                        result.put("latitude", Double.parseDouble(yStr));
                        return result;
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid coordinate format received from API");
                    }
                }
            }
            throw new RuntimeException("Failed to get geocoding data");
        }catch (Exception e) {
            throw new RuntimeException("Error :" + e.getMessage());
        }
    }
    @Override
    public Place createPlace(PlaceRequestDTO.PlaceCreateDTO request, Double longitude, Double latitude) {
        Place place = PlaceConverter.toPlace(request, longitude, latitude);
        placeRepository.save(place);
        return place;
    }

    @Override
    public PlaceResponseDTO.PlacePreviewDTO previewPlace(Long placeId, Member member) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(()-> new EntityNotFoundException("Place가 없습니다."));
        Boolean isSaved = memberService.isSaved(member, place);
        List<String> placeImgList = placeImgRepository.findAllUrlByPlace(place);
        PlaceResponseDTO.PlacePreviewDTO response = PlaceConverter.toPlacePreviewDTO(place, isSaved, placeImgList);
        return response;
    }

    @Override
    public PlaceResponseDTO.PlaceDetailDTO detailPlace(Long placeId, Member member) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(()-> new EntityNotFoundException("Place가 없습니다."));
        Boolean isSaved = memberService.isSaved(member, place);
        List<String> placeImgList = placeImgRepository.findAllUrlByPlace(place);
        PlaceResponseDTO.PlaceDetailDTO response = PlaceConverter.toPlaceDetailDTO(place, isSaved, placeImgList);
        return response;
    }

    @Override
    public Page<Place> findNearbyPlaces(Double longitude, Double latitude, Double radius, Integer page) {
        PageRequest pageRequest = PageRequest.of(page-1, 10);
        double latRange = radius / EARTH_RADIUS * (180 / Math.PI);
        double lonRange = radius / (EARTH_RADIUS * Math.cos(Math.toRadians(latitude))) * (180 / Math.PI);
        double minLat = latitude - latRange;
        double maxLat = latitude + latRange;
        double minLng = longitude - lonRange;
        double maxLng = longitude + lonRange;

        //반경 내 조회
        return placeRepository.findByBoundingBox(minLat, maxLat, minLng, maxLng,
                latitude, longitude, radius, pageRequest);

    }
    @Override
    public Page<Place> searchPlaces(String search, Integer page) {
        PageRequest pageRequest = PageRequest.of(page-1, 10);
        Page<Place> searchPlaces = placeRepository.findByNameContainingOrAddressContaining(search, pageRequest);
        return searchPlaces;
    }
}