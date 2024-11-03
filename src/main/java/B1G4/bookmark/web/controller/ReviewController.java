package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.security.handler.annotation.AuthUser;
import B1G4.bookmark.service.BookService.BookServiceImpl;
import B1G4.bookmark.service.ReviewService.ReviewImgServiceImpl;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewRequestDTO;
import B1G4.bookmark.service.ReviewService.ReviewServiceImpl;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl reviewService;
    private final ReviewImgServiceImpl reviewImageService;
    private final BookServiceImpl bookService;

    @PostMapping(value = "/reviews/{placeId}", consumes = "multipart/form-data")
    public BaseResponse<ReviewResponseDTO.ReviewIdDTO> createReview(
            @PathVariable Long placeId,
            @AuthUser Member member,
            @RequestPart("reviewData") ReviewRequestDTO reviewRequestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        Long memberId = member.getId();

        // 리뷰 저장
        Long reviewId = reviewService.createReview(placeId, memberId, reviewRequestDTO);

        // 책 정보 저장
        bookService.addBooksToPlace(placeId, reviewRequestDTO.getBooks());

        // 이미지 저장
        if (images != null && !images.isEmpty()) {
            reviewImageService.uploadImage(reviewId, images);
        }

        // ReviewIdDTO 반환
        ReviewResponseDTO.ReviewIdDTO responseDTO = ReviewResponseDTO.ReviewIdDTO.builder()
                .reviewId(reviewId)
                .build();

        return BaseResponse.of(SuccessStatus.REVIEW_CREATE_OK, responseDTO);
    }

    @GetMapping("/reviews/{placeId}")
    public BaseResponse<ReviewResponseDTO.ReviewListDTO> getReviewsByPlace(
            @PathVariable Long placeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ReviewResponseDTO.ReviewPreviewDTO> reviews = reviewService.getReviewsByPlace(placeId, page, size);

        ReviewResponseDTO.ReviewListDTO responseDTO = ReviewResponseDTO.ReviewListDTO.builder()
                .reviewPreviewList(reviews.getContent())
                .totalPages(reviews.getTotalPages())
                .totalElements(reviews.getTotalElements())
                .isFirst(reviews.isFirst())
                .isLast(reviews.isLast())
                .build();

        return BaseResponse.of(SuccessStatus.REVIEW_FETCH_OK, responseDTO);
    }
}
