package B1G4.bookmark.repository;

import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByPlace(Place place, Pageable pageable);
}
