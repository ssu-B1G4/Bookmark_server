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
import B1G4.bookmark.web.dto.ReviewDTO.ReviewResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;

    @Transactional
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

    @Transactional
    public Page<ReviewResponseDTO.ReviewPreviewDTO> getReviewsByPlace(Long placeId, int page, int size) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 공간 없음 " + placeId));

        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findByPlace(place, pageable);

        return reviews.map(ReviewConverter::toReviewPreviewDTO);
    }
}
