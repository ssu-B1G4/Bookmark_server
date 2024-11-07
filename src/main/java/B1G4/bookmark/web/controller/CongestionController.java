package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.service.CongestionService.CongestionServiceImpl;
import B1G4.bookmark.web.dto.CongestionDTO.CongestionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CongestionController {

    private final CongestionServiceImpl congestionServiceImpl;

    @GetMapping("/congestions/{placeId}/graph")
    public BaseResponse<CongestionResponseDTO> getCongestionGraph(@PathVariable Long placeId) {
        CongestionResponseDTO congestionData = congestionServiceImpl.getCongestionGraph(placeId);
        return BaseResponse.of(SuccessStatus.CONGESTION_FETCH_OK, congestionData);
    }
}
