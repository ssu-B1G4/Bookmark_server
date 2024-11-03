package B1G4.bookmark.service.ReviewService;

import B1G4.bookmark.converter.ReviewConverter;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.Review;
import B1G4.bookmark.domain.enums.Mood;
import B1G4.bookmark.repository.MemberRepository;
import B1G4.bookmark.repository.PlaceRepository;
import B1G4.bookmark.repository.ReviewRepository;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;

    public Long createReview(Long placeId, Long memberId,ReviewRequestDTO reviewRequestDTO) {

        // Place 및 Member 조회
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + placeId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));

        Review review = ReviewConverter.toReview(reviewRequestDTO, place, member);

        reviewRepository.save(review);

        for (Mood mood: reviewRequestDTO.getMoods()) {
            place.incrementMoodCount(mood);
        }

        place.updateMoods();

        //리뷰 등록시 공간 리뷰개수 + 1
        place.addReviewCount();
        placeRepository.save(place);

        return review.getId();
    }

    // 특정 장소의 리뷰에서 분위기를 집계하여 대표 분위기 2개 갱신
    private void updatePlaceMoods(Place place, List<Mood> newReviewMoods) {
       Map<Mood, Long> moodCOunt = new HashMap<>();

       List<Review> reviews = reviewRepository.findByPlace(place);
       for (Review review: reviews) {

       }
    }
}
