package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.service.CongestionService.CongestionServiceImpl;
import B1G4.bookmark.web.dto.CongestionDTO.CongestionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CongestionController {

    private final CongestionServiceImpl congestionServiceImpl;


    @Operation(
            summary = "혼잡도 그래프 조회",
            description = "특정 공간의 운영 시간에 맞춘 시간대별 혼잡도 데이터를 반환합니다."
    )
    @GetMapping("/congestions/{placeId}/graph")
    public BaseResponse<CongestionResponseDTO> getCongestionGraph(
            @Parameter(description = "공간 ID", required = true)
            @PathVariable Long placeId) {

        CongestionResponseDTO congestionData = congestionServiceImpl.getCongestionGraph(placeId);
        return BaseResponse.of(SuccessStatus.CONGESTION_FETCH_OK, congestionData);
    }
}
