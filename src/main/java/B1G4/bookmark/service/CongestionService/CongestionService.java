package B1G4.bookmark.service.CongestionService;

import B1G4.bookmark.web.dto.CongestionDTO.CongestionResponseDTO;

public interface CongestionService {
    public CongestionResponseDTO getCongestionGraph(Long placeId);
}
