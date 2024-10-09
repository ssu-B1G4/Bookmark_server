package B1G4.bookmark.repository;

import B1G4.bookmark.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
