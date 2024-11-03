package B1G4.bookmark.service.ReviewService;

import B1G4.bookmark.web.dto.ReviewDTO.ReviewRequestDTO;

public interface ReviewService {
    public Long createReview(Long placeId, Long memberId, ReviewRequestDTO reviewRequestDTO);

}
