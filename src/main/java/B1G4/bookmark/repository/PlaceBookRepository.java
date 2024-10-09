package B1G4.bookmark.repository;

import B1G4.bookmark.domain.PlaceBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceBookRepository extends JpaRepository<PlaceBook, Long> {
}
