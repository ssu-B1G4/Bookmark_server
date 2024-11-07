package B1G4.bookmark.service.ReviewService;

import B1G4.bookmark.converter.CongestionConverter;
import B1G4.bookmark.converter.PlaceConverter;
import B1G4.bookmark.converter.ReviewConverter;
import B1G4.bookmark.domain.Congestion;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.Review;
import B1G4.bookmark.domain.enums.Mood;
import B1G4.bookmark.repository.CongestionRepository;
import B1G4.bookmark.repository.MemberRepository;
import B1G4.bookmark.repository.PlaceRepository;
import B1G4.bookmark.repository.ReviewRepository;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewRequestDTO;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final CongestionRepository congestionRepository;

    @Transactional
    public Long createReview(Long placeId, Long memberId, ReviewRequestDTO reviewRequestDTO) {

        // Place 및 Member 조회
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + placeId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));

        Review review = ReviewConverter.toReview(reviewRequestDTO, place, member);
        reviewRepository.save(review);

        updateCongestion(place, reviewRequestDTO);

        for (Mood mood : reviewRequestDTO.getMoods()) {
            place.incrementMoodCount(mood);
        }

        place.updateMoods();

        //리뷰 등록시 공간 리뷰개수 + 1
        place.addReviewCount();
        placeRepository.save(place);

        return review.getId();
    }

    @Transactional
    public Page<ReviewResponseDTO.ReviewPreviewDTO> getReviewsByPlace(Long placeId, int page) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 공간 없음 " + placeId));

        Pageable pageable = PageRequest.of((page-1), 10);
        Page<Review> reviews = reviewRepository.findByPlace(place, pageable);

        return reviews.map(ReviewConverter::toReviewPreviewDTO);
    }

    private void updateCongestion(Place place, ReviewRequestDTO reviewRequestDTO) {
        Congestion congestion = place.getCongestion();
        if (congestion == null) {
            congestion = Congestion.builder()
                    .zero(0f).one(0f).two(0f).three(0f).four(0f).five(0f)
                    .six(0f).seven(0f).eight(0f).nine(0f).ten(0f)
                    .eleven(0f).twelve(0f).thirteen(0f).fourteen(0f)
                    .fifteen(0f).sixteen(0f).seventeen(0f).eighteen(0f)
                    .nineteen(0f).twenty(0f).twentyOne(0f).twentyTwo(0f)
                    .twentyThree(0f).twentyFour(0f).
                    build();

            congestionRepository.save(congestion);
            place = PlaceConverter.withUpdatedCongestion(place, congestion);
            placeRepository.save(place);
        }

        // 방문 시간 범위와 혼잡도 가져오기
        LocalTime startTime = reviewRequestDTO.getVisitStartTime();
        LocalTime endTime = reviewRequestDTO.getVisitEndTime();
        int congestionLevel = reviewRequestDTO.getCongestion();

        // 기존 리뷰 개수
        int totalReviewCount = place.getReviewCount();

        // 혼잡도 업데이트
        Congestion updatedCongestion = CongestionConverter.updateCongestion(congestion, startTime, endTime, congestionLevel, totalReviewCount);

        // 업데이트된 혼잡도 저장
        congestionRepository.save(updatedCongestion);
    }
}
