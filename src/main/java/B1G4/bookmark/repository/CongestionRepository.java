package B1G4.bookmark.repository;

import B1G4.bookmark.domain.Congestion;
import B1G4.bookmark.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongestionRepository extends JpaRepository<Congestion, Long> {
    Congestion findByPlace(Place place);
}
