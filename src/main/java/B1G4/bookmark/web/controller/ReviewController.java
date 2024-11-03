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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private final BookServiceImpl bookService;

    @Operation(
            summary = "리뷰 생성",
            description = "특정 장소에 대한 리뷰를 생성합니다. " +
                          "리뷰 내용과 이미지 파일 목록을 함께 전송하여 리뷰를 생성할 수 있습니다."
    )
    @PostMapping(value = "/reviews/{placeId}", consumes = "multipart/form-data")
    public BaseResponse<ReviewResponseDTO> createReview(
            @Parameter(description = "리뷰를 등록할 장소 ID", required = true)
            @PathVariable Long placeId,

            @Parameter(name = "user", hidden = true)
            @AuthUser Member member,

            @Parameter(description = "리뷰 내용을 포함한 데이터", required = true)
            @RequestPart("reviewData") ReviewRequestDTO reviewRequestDTO,

            @Parameter(description = "리뷰와 함께 업로드할 이미지 파일 목록")
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        Long memberId = member.getId();

        // 리뷰 저장
        Long reviewId = reviewService.createReview(placeId, memberId, reviewRequestDTO);

        // 해당 공간이 보유하고 있는 책 저장
        bookService.addBooksToPlace(placeId, reviewRequestDTO.getBooks());

        // 이미지 저장
        if (images != null && !images.isEmpty()) {
            reviewImageService.uploadImage(reviewId, images);
        }

        ReviewResponseDTO responseDTO = new ReviewResponseDTO(reviewId);
        return BaseResponse.of(SuccessStatus.REVIEW_CREATE_OK, responseDTO);
    }
}
