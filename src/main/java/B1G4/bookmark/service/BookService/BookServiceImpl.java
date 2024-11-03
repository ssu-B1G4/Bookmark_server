package B1G4.bookmark.service.BookService;

import B1G4.bookmark.domain.Book;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.repository.BookRepository;
import B1G4.bookmark.repository.PlaceBookRepository;
import B1G4.bookmark.web.dto.ReviewDTO.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;

    public void saveBooks(List<BookDTO> books, Place place) {
        if (books != null && !books.isEmpty()) {
            List<Book> bookEntities = books.stream()
                    .map(b -> new Book(b.getTitle(), b.getAuthor()))
                    .collect(Collectors.toList());
            bookRepository.saveAll(bookEntities);
        }
    }
}
