package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewRequestDTO;
import B1G4.bookmark.service.ReviewService.ReviewServiceImpl;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl reviewServiceImpl;

    // memberId 파라미터로 받았는데 로그인 구현되면 jwt에서 뽑아오는걸로 바꿀게요
    @PostMapping(value = "/reviews/{placeId}/{memberId}", consumes = "multipart/form-data")
    public BaseResponse<ReviewResponseDTO> createReview(
            @PathVariable Long placeId,
            @PathVariable Long memberId,
            @RequestPart("reviewData") ReviewRequestDTO reviewRequestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images  // 불필요한 쉼표 제거
    ) {
        Long reviewId = reviewServiceImpl.createReview(placeId, memberId, reviewRequestDTO, images);
        ReviewResponseDTO responseDTO = new ReviewResponseDTO(reviewId);
        return BaseResponse.of(SuccessStatus.REVIEW_CREATE_OK, responseDTO);
    }
}