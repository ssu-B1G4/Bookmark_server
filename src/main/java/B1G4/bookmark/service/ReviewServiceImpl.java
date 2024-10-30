package B1G4.bookmark.service;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.Review;
import B1G4.bookmark.repository.MemberRepository;
import B1G4.bookmark.repository.PlaceRepository;
import B1G4.bookmark.repository.ReviewRepository;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ReviewImgServiceImpl reviewImgServiceImpl;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;

    public Long createReview(Long placeId, Long memberId,ReviewRequestDTO reviewRequestDTO, List<MultipartFile> images) {

        // Place 및 Member 조회
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + placeId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));

        Review review = Review.builder()
                .content(reviewRequestDTO.getContent())
                .visitDate(reviewRequestDTO.getVisitDate())
                .outlet(reviewRequestDTO.getOutlet())
                .size(reviewRequestDTO.getSize())
                .wifi(reviewRequestDTO.getWifi())
                .noise(reviewRequestDTO.getNoise())
                .writtenDate(LocalDateTime.now())
                .place(place)           // Place 연결
                .member(member)         // Member 연결
                .build();

        reviewRepository.save(review);

        reviewImgServiceImpl.uploadImage(review, images);
        return review.getId();
    }
}
