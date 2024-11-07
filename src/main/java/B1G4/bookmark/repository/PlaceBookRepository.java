package B1G4.bookmark.repository;

import B1G4.bookmark.domain.Book;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.PlaceBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlaceBookRepository extends JpaRepository<PlaceBook, Long> {

    Optional<PlaceBook> findByPlaceAndBook(Place place, Book book);

    @Query("SELECT pb.book FROM PlaceBook pb WHERE pb.place = :place")
    Page<Book> findBooksByPlace(@Param("place")Place place, Pageable pageable);
}
