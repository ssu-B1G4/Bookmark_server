package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.security.handler.annotation.AuthUser;
import B1G4.bookmark.service.ReportPlaceService.ReportPlaceServiceImpl;
import B1G4.bookmark.web.dto.ReportPlaceDTO.ReportPlaceRequestDTO;
import B1G4.bookmark.web.dto.ReportPlaceDTO.ReportPlaceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportPlaceController {

    private final ReportPlaceServiceImpl reportPlaceService;

    @Operation(summary = "공간 제보", description = "사용자가 공간에 대한 제보를 생성합니다.")
    @PostMapping(value = "/report-place", consumes = "multipart/form-data")
    public BaseResponse<ReportPlaceResponseDTO> createReportPlace(
            @Parameter(description = "공간 제보 데이터", required = true)
            @RequestPart("reportData") ReportPlaceRequestDTO reportPlaceRequestDTO,

            @Parameter(name = "user", hidden = true)
            @AuthUser Member member,

            @Parameter(description = "제보에 사용할 이미지 목록")
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        ReportPlaceResponseDTO responseDTO = reportPlaceService.createReportPlace(reportPlaceRequestDTO, images, member);
        return BaseResponse.of(SuccessStatus.REPORT_PLACE_CREATE_OK, responseDTO);
    }
}
