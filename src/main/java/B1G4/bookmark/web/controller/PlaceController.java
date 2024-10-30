package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.converter.PlaceConverter;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.repository.PlaceRepository;
import B1G4.bookmark.service.PlaceService.PlaceServiceImpl;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceRequestDTO;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceServiceImpl placeService;
    private final PlaceRepository placeRepository;
    @PostMapping("/places")
    public BaseResponse<PlaceResponseDTO.PlaceIdDTO> getGeoData(@RequestBody PlaceRequestDTO.PlaceCreateDTO request) {
        Map<String, Double> geoData = placeService.getGeoData(request.getAddress());
        Place place = PlaceConverter.toPlace(request, geoData.get("longitude"), geoData.get("latitude"));
        placeRepository.save(place);
        PlaceResponseDTO.PlaceIdDTO response = PlaceConverter.toPlaceIdDTO(place);
        return BaseResponse.of(SuccessStatus.PLACE_CREATE_OK, response);
    }
}
