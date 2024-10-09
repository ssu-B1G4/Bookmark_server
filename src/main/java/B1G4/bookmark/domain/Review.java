package B1G4.bookmark.domain;

import B1G4.bookmark.domain.enums.Noise;
import B1G4.bookmark.domain.enums.Outlet;
import B1G4.bookmark.domain.enums.Size;
import B1G4.bookmark.domain.enums.Wifi;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    // 리뷰 작성일
    private LocalDateTime writtenDate;

    // 방문 날짜
    private LocalDateTime visitDate;

    @Enumerated(EnumType.STRING)
    private Outlet outlet;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Wifi wifi;

    @Enumerated(EnumType.STRING)
    private Noise noise;

    // place 설정
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImg> reviewImgs = new ArrayList<>();

    // 리뷰 이미지 추가
    public void addReviewImg(ReviewImg reviewImg) {
        reviewImgs.add(reviewImg);
        reviewImg.setReview(this);
    }

    // 리뷰 이미지 제거
    public void removeReviewImg(ReviewImg reviewImg) {
        reviewImgs.remove(reviewImg);
        reviewImg.setReview(null);
    }
}
