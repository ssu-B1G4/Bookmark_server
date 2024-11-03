package B1G4.bookmark.repository;

import B1G4.bookmark.domain.Book;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.PlaceBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceBookRepository extends JpaRepository<PlaceBook, Long> {

    Optional<PlaceBook> findByPlaceAndBook(Place place, Book book);
}
