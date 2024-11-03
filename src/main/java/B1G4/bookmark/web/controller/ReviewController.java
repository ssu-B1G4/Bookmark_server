package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.security.handler.annotation.AuthUser;
import B1G4.bookmark.service.ReviewService.ReviewImgServiceImpl;
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

    private final ReviewServiceImpl reviewService;
    private final ReviewImgServiceImpl reviewImageService;

    @PostMapping(value = "/reviews/{placeId}", consumes = "multipart/form-data")
    public BaseResponse<ReviewResponseDTO> createReview(
            @PathVariable Long placeId,
            @AuthUser Member member,
            @RequestPart("reviewData") ReviewRequestDTO reviewRequestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        Long memberId = member.getId();

        // 리뷰 저장
        Long reviewId = reviewService.createReview(placeId, memberId, reviewRequestDTO);

        // 이미지 저장
        if (images != null && !images.isEmpty()) {
            reviewImageService.uploadImage(reviewId, images);
        }

        ReviewResponseDTO responseDTO = new ReviewResponseDTO(reviewId);
        return BaseResponse.of(SuccessStatus.REVIEW_CREATE_OK, responseDTO);
    }
}
