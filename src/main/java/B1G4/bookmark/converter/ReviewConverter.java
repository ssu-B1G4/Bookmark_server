package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.Review;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewRequestDTO;

import java.time.LocalDateTime;

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
}
