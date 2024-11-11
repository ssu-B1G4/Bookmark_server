package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.ReportPlace;
import B1G4.bookmark.web.dto.ReportPlaceDTO.ReportPlaceRequestDTO;
import B1G4.bookmark.web.dto.ReportPlaceDTO.ReportPlaceResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ReportPlaceConverter {

    // ReportPlaceRequestDTO -> ReportPlace 엔티티로 변환
    public static ReportPlace toEntity(ReportPlaceRequestDTO requestDTO, List<String> imageUrls, Member member) {
        return ReportPlace.builder()
                .name(requestDTO.getName())
                .address(requestDTO.getAddress())
                .content(requestDTO.getContent())
                .moods(requestDTO.getMoods())
                .outlet(requestDTO.getOutlet())
                .size(requestDTO.getSize())
                .wifi(requestDTO.getWifi())
                .noise(requestDTO.getNoise())
                .category(requestDTO.getCategory())
                .author(requestDTO.getAuthor())
                .title(requestDTO.getTitle())
                .imageUrls(imageUrls)
                .member(member)
                .build();
    }
}