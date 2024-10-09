package B1G4.bookmark.repository;

import B1G4.bookmark.domain.UserPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlaceRepository extends JpaRepository<UserPlace, Long> {
}
