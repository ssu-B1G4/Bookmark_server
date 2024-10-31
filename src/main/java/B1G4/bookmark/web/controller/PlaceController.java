package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.converter.PlaceConverter;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.repository.MemberRepository;
import B1G4.bookmark.service.PlaceService.PlaceServiceImpl;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceRequestDTO;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceServiceImpl placeService;
    private final MemberRepository memberRepository;
    private final MemberServiceImpl memberService;
    private final PlaceImgRepository placeImgRepository;
    @Operation(summary = "공간 등록(위도, 경도 추출), 연결용 x", description = "도로명 주소로 위도, 경도를 추출하여 공간을 DB에 저장합니다. 프론트와 연결용은 아닙니다!")
    @PostMapping("/places")
    public BaseResponse<PlaceResponseDTO.PlaceIdDTO> getGeoData(@RequestBody PlaceRequestDTO.PlaceCreateDTO request) {
        Map<String, Double> geoData = placeService.getGeoData(request.getAddress());
        Place place = placeService.createPlace(request, geoData.get("longitude"), geoData.get("latitude"));
        PlaceResponseDTO.PlaceIdDTO response = PlaceConverter.toPlaceIdDTO(place);
        return BaseResponse.of(SuccessStatus.PLACE_CREATE_OK, response);
    }

    //TODO:로그인 구현 후 memberId 제거
    @Operation(summary = "공간 미리보기", description = "한 공간의 정보 미리보기 입니다.")
    @Parameters({
            @Parameter(name = "placeId", description = "조회하려는 공간 id")
    })
    @GetMapping("/places/{placeId}/preview/{memberId}")
    public BaseResponse<PlaceResponseDTO.PlacePreviewDTO> previewPlace(@PathVariable Long memberId, @PathVariable Long placeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new EntityNotFoundException("Member가 없습니다"));
        PlaceResponseDTO.PlacePreviewDTO response = placeService.previewPlace(placeId, member);
        return BaseResponse.of(SuccessStatus.PLACE_PREVIEW_OK, response);

    }

    //TODO:로그인 구현 후 memberId 제거
    @Operation(summary = "공간 상세보기", description = "한 공간의 상세정보를 조회합니다.")
    @Parameters({
            @Parameter(name = "placeId", description = "조회하려는 공간 id")
    })
    @GetMapping("/places/{placeId}/detail/{memberId}")
    public BaseResponse<PlaceResponseDTO.PlaceDetailDTO> detailPlace(@PathVariable Long memberId, @PathVariable Long placeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new EntityNotFoundException("Member가 없습니다"));
        PlaceResponseDTO.PlaceDetailDTO response = placeService.detailPlace(placeId, member);
        return BaseResponse.of(SuccessStatus.PLACE_DETAIL_OK, response);
    }
}
