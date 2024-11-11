package B1G4.bookmark.service.ReportPlaceService;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.web.dto.ReportPlaceDTO.ReportPlaceRequestDTO;
import B1G4.bookmark.web.dto.ReportPlaceDTO.ReportPlaceResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReportPlaceService {
    ReportPlaceResponseDTO createReportPlace(ReportPlaceRequestDTO requestDTO, List<MultipartFile> images, Member member);
}
