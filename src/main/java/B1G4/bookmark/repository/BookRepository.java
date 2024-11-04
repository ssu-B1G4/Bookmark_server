package B1G4.bookmark.repository;

import B1G4.bookmark.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByAuthorAndTitle(String author, String title);
}
