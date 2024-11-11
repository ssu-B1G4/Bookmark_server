package B1G4.bookmark.repository;

import B1G4.bookmark.domain.OperatingTime;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperatingTimeRepository extends JpaRepository<OperatingTime, Long> {
    List<OperatingTime> findAllByPlace(Place place);
    OperatingTime findByPlaceAndDayOfWeek(Place place, DayOfWeek dayOfWeek);
}
