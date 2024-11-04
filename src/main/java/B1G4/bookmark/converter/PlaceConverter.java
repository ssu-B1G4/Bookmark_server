package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.UserPlace;
import B1G4.bookmark.repository.PlaceImgRepository;
import B1G4.bookmark.repository.UserPlaceRepository;
import B1G4.bookmark.service.MemberService.MemberServiceImpl;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceRequestDTO;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<String> moods = Arrays.asList(place.getMood1().getViewName(), place.getMood2().getViewName());
        return PlaceResponseDTO.PlacePreviewDTO.builder()
                .placeId(place.getId())
                .isSaved(isSaved)
                .moods(moods)
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
    public static PlaceResponseDTO.PlaceDetailDTO toPlaceDetailDTO(Place place, Boolean isSaved, List<String> placeImgList, Map<String, Map<String, String>> operatingTimeList) {
        List<String> moods = Arrays.asList(place.getMood1().getViewName(), place.getMood2().getViewName());
        return PlaceResponseDTO.PlaceDetailDTO.builder()
                .placeId(place.getId())
                .size(place.getSize().getViewName())
                .wifi(place.getWifi().getViewName())
                .moods(moods)
                .url(place.getUrl())
                .phone(place.getPhone())
                .name(place.getName())
                .noise(place.getNoise().getViewName())
                .placeImgList(placeImgList)
                .category(place.getCategory().getViewName())
                .longitude(place.getLongitude())
                .latitude(place.getLatitude())
                .phone(place.getPhone())
                .isSaved(isSaved)
                .address(place.getAddress())
                .outlet(place.getOutlet().getViewName())
                .operatingTimeList(operatingTimeList)
                .build();
    }
    public static PlaceResponseDTO.PlacePreviewListDTO toPlacePreviewList(Page<Place> placeList, Member member, MemberServiceImpl memberService, PlaceImgRepository placeImgRepository) {
        List<PlaceResponseDTO.PlacePreviewDTO> placePreviewDTOList = placeList.stream()
                .map(place -> toPlacePreviewDTO(
                        place,
                        memberService.isSaved(member, place),
                        placeImgRepository.findAllUrlByPlace(place)
                ))
                .collect(Collectors.toList());
        return PlaceResponseDTO.PlacePreviewListDTO.builder()
                .placePreviewDTOList(placePreviewDTOList)
                .listSize(placeList.getSize())
                .isFirst(placeList.isFirst())
                .isLast(placeList.isLast())
                .totalElements(placeList.getTotalElements())
                .totalPage(placeList.getTotalPages())
                .build();
    }

    public static UserPlace toUserPlace(Member member, Place place){
        return UserPlace.builder()
                .member(member)
                .place(place)
                .build();
    }
}
