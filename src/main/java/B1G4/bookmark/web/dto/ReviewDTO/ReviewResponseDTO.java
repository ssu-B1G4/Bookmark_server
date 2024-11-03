package B1G4.bookmark.web.dto.ReviewDTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDTO {
    // 리뷰 ID만 반환하는 DTO -> 리뷰 생성 시 사용
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewIdDTO {
        private Long reviewId;
    }

    // 리뷰 목록 조회 시 요약 정보만 반환하는 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPreviewDTO {
        private Long reviewId;
        private String nickname;         // 닉네임
        private LocalDateTime visitDate; // 방문 날짜
        private String content;          // 리뷰 내용
        private List<String> reviewImgs; // 리뷰 이미지 URL 리스트
    }

    // 리뷰 목록 조회 시 페이징 정보를 포함하는 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewListDTO {
        private List<ReviewPreviewDTO> reviewPreviewList;
        private Integer totalPages;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

    // 리뷰 상세 조회 시 모든 정보를 포함하는 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDetailDTO {
        private Long reviewId;
        private String nickname;
        private LocalDateTime visitDate;
        private LocalDateTime writtenDate;
        private String content;
        private String outlet;
        private String size;
        private String wifi;
        private String noise;
        private List<String> moods;
        private List<String> reviewImgs;
    }
}
