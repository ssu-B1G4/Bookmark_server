package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.Review;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewRequestDTO;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewResponseDTO;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

// ReviewRequestDTO를 Review 엔티티로 변환
public class ReviewConverter {
    public static Review toReview(ReviewRequestDTO request, Place place, Member member) {
        return Review.builder()
                .content(request.getContent())
                .visitDate(request.getVisitDate())
                .outlet(request.getOutlet())
                .size(request.getSize())
                .wifi(request.getWifi())
                .noise(request.getNoise())
                .writtenDate(LocalDateTime.now())
                .place(place)           // Place 연결
                .member(member)         // Member 연결
                .build();
    }

    // Review 엔티티를 ReviewIdDTO로 변환
    public static ReviewResponseDTO.ReviewIdDTO toReviewIdDTO(Review review) {
        return ReviewResponseDTO.ReviewIdDTO.builder()
                .reviewId(review.getId())
                .build();
    }

    // Review 엔티티를 ReviewPreviewDTO로 변환
    public static ReviewResponseDTO.ReviewPreviewDTO toReviewPreviewDTO(Review review) {
        return ReviewResponseDTO.ReviewPreviewDTO.builder()
                .reviewId(review.getId())
                .nickname(review.getMember().getNickname()) // Member의 nickname을 사용
                .visitDate(review.getVisitDate())
                .content(review.getContent())
                .reviewImgs(review.getReviewImgs().stream()
                        .map(img -> img.getUrl())
                        .collect(Collectors.toList()))
                .build();
    }

    // Review 엔티티를 상세 정보용 ReviewDetailDTO로 변환
    public static ReviewResponseDTO.ReviewDetailDTO toReviewDetailDTO(Review review) {
        return ReviewResponseDTO.ReviewDetailDTO.builder()
                .reviewId(review.getId())
                .nickname(review.getMember().getNickname()) // Member의 nickname을 사용
                .visitDate(review.getVisitDate())
                .writtenDate(review.getWrittenDate())
                .content(review.getContent())
                .outlet(review.getOutlet().name())
                .size(review.getSize().name())
                .wifi(review.getWifi().name())
                .noise(review.getNoise().name())
                .reviewImgs(review.getReviewImgs().stream()
                        .map(img -> img.getUrl())
                        .collect(Collectors.toList()))
                .build();
    }
}
