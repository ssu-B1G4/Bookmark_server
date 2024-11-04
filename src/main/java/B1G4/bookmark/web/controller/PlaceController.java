package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.converter.PlaceConverter;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.repository.MemberRepository;
import B1G4.bookmark.repository.PlaceImgRepository;
import B1G4.bookmark.security.handler.annotation.AuthUser;
import B1G4.bookmark.service.MemberService.MemberServiceImpl;
import B1G4.bookmark.service.PlaceService.PlaceServiceImpl;
import B1G4.bookmark.web.dto.MemberDTO.MemberResponseDTO;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceRequestDTO;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    //TODO : 로그인 구현 후 memberId 제거
    @Operation(summary = "근처 추천 공간", description = "현재 사용자의 위치를 기반으로 반경 5km 내의 공간들을 조회합니다. 가까운 순으로 정렬되어 있습니다.")
    @Parameters({
            @Parameter(name = "nowLongitude", description = "현재 사용자 위치의 경도"),
            @Parameter(name = "nowLatitude", description = "현재 사용자 위치의 위도"),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다."),
            @Parameter(name = "mood", description = "분위기 필터, 편안한/신나는/차분한/즐거운/아늑한/재미있는 중에 선택"),
            @Parameter(name = "day", description = "영업시간 필터(요일), 반드시 시간과 함께 보내주세요. ex) 월요일"),
            @Parameter(name = "time", description = "영업시간 필터(시간), 반드시 요일과 함께 보내주세요. ex) 14:00"),
            @Parameter(name = "size", description = "공간크기 필터, 부족/보통/넉넉 중에 선택"),
            @Parameter(name = "outlet", description = "콘센트 필터, 부족/보통/넉넉 중에 선택"),
            @Parameter(name = "noise", description = "소음 필터, 조용함/보통/생기있음 중에 선택"),
            @Parameter(name = "wifi", description = "와이파이 필터, 있어요/없어요 중에 선택")

    })
    @GetMapping("/places/nearby/{memberId}")
    public BaseResponse<PlaceResponseDTO.PlacePreviewListDTO> getNearbyPlaceList(
            @RequestParam(name = "nowLongitude") Double nowLongitude,
            @RequestParam(name = "nowLatitude") Double nowLatitude,
            @RequestParam(name = "page") Integer page,
            @RequestParam(required = false) String mood,
            @RequestParam(required = false) String day,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String outlet,
            @RequestParam(required = false) String noise,
            @RequestParam(required = false) String wifi,
            @PathVariable Long memberId
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new EntityNotFoundException("Member가 없습니다"));
        //TODO : 반경 - 5.0km, size - 10개, 추후 상의 후 변경
        Page<Place> placeList = placeService.findNearbyPlaces(nowLongitude, nowLatitude, 5.0, page);
        Page<Place> filteredPlaceList = placeService.addFilter(placeList, mood, day, time, size, outlet, noise, wifi);
        PlaceResponseDTO.PlacePreviewListDTO response = PlaceConverter.toPlacePreviewList(filteredPlaceList, member, memberService, placeImgRepository);
        return BaseResponse.of(SuccessStatus.NEARBY_PLACE_OK, response);
    }
    //TODO:로그인 구현 후 memberId 제거
    @Operation(summary = "공간 검색 결과 조회", description = "공간 검색 결과를 조회합니다. 검색 범위는 공간 이름과 공간 주소입니다.")
    @Parameters({
            @Parameter(name = "search", description = "검색어"),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다."),
            @Parameter(name = "mood", description = "분위기 필터, 편안한/신나는/차분한/즐거운/아늑한/재미있는 중에 선택"),
            @Parameter(name = "day", description = "영업시간 필터(요일), 반드시 시간과 함께 보내주세요. ex) 월요일"),
            @Parameter(name = "time", description = "영업시간 필터(시간), 반드시 요일과 함께 보내주세요. ex) 14:00"),
            @Parameter(name = "size", description = "공간크기 필터, 부족/보통/넉넉 중에 선택"),
            @Parameter(name = "outlet", description = "콘센트 필터, 부족/보통/넉넉 중에 선택"),
            @Parameter(name = "noise", description = "소음 필터, 조용함/보통/생기있음 중에 선택"),
            @Parameter(name = "wifi", description = "와이파이 필터, 있어요/없어요 중에 선택")

    })
    @GetMapping("/places/{memberId}")
    public BaseResponse<PlaceResponseDTO.PlacePreviewListDTO> searchPlace(
            @RequestParam(name = "search") String search,
            @RequestParam(name = "page") Integer page,
            @RequestParam(required = false) String mood,
            @RequestParam(required = false) String day,
            @RequestParam(required = false) String time,
            @RequestParam(required = false)String size,
            @RequestParam(required = false) String outlet,
            @RequestParam(required = false) String noise,
            @RequestParam(required = false) String wifi,
            @PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new EntityNotFoundException("Member가 없습니다"));
        Page<Place> placeList = placeService.searchPlaces(search, page);
        Page<Place> filteredPlaceList = placeService.addFilter(placeList, mood, day, time, size, outlet, noise, wifi);
        PlaceResponseDTO.PlacePreviewListDTO response = PlaceConverter.toPlacePreviewList(filteredPlaceList, member, memberService, placeImgRepository);
        return BaseResponse.of(SuccessStatus.SEARCH_PLACE_OK, response);
    }

    @Operation(summary = "공간 저장(북마크) API", description = "공간을 저장(북마크)하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @PostMapping("/place/{placeId}/bookmark")
    public BaseResponse<String> bookmarkPlace(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                                                        @PathVariable Long placeId) {
        placeService.bookmarkPlace(member, placeId);
        return BaseResponse.onSuccess(SuccessStatus.BOOKMARK_PLACE_OK.getMessage());
    }

    @Operation(summary = "공간 저장(북마크) 해제 API", description = "공간을 저장(북마크) 해제하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @DeleteMapping("/place/{placeId}/bookmark")
    public BaseResponse<String> unbookmarkPlace(@Parameter(name = "user", hidden = true) @AuthUser Member member,
                                              @PathVariable Long placeId) {
        placeService.unbookmarkPlace(member, placeId);
        return BaseResponse.onSuccess(SuccessStatus.UNBOOKMARK_PLACE_OK.getMessage());
    }

}
