package B1G4.bookmark.repository;

import B1G4.bookmark.domain.OperatingTime;
import B1G4.bookmark.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperatingTimeRepository extends JpaRepository<OperatingTime, Long> {
    List<OperatingTime> findAllByPlace(Place place);
}
