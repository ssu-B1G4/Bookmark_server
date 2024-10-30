package B1G4.bookmark.service;

import B1G4.bookmark.web.dto.ReviewDTO.ReviewRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    public Long createReview(Long placeId, Long memberId, ReviewRequestDTO reviewRequestDTO, List<MultipartFile> images);

}
