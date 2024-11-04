package B1G4.bookmark.service.BookService;

import B1G4.bookmark.domain.Book;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.PlaceBook;
import B1G4.bookmark.repository.BookRepository;
import B1G4.bookmark.repository.PlaceBookRepository;
import B1G4.bookmark.repository.PlaceRepository;
import B1G4.bookmark.web.dto.ReviewDTO.BookDTO;
import B1G4.bookmark.web.dto.ReviewDTO.ReviewRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    private final PlaceBookRepository placeBookRepository;
    private final PlaceRepository placeRepository;

    public void addBooksToPlace(Long placeId, List<BookDTO> bookDTOs) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 공간 없음"));

        if (bookDTOs != null) {
            bookDTOs.forEach(bookDTO -> {
                Book book = bookRepository.findByAuthorAndTitle(bookDTO.getAuthor(), bookDTO.getTitle())
                        .orElseGet(() -> bookRepository.save(
                                Book.builder()
                                        .author(bookDTO.getAuthor())
                                        .title(bookDTO.getTitle())
                                        .build()
                        ));

                if (placeBookRepository.findByPlaceAndBook(place, book).isEmpty()) {
                    placeBookRepository.save(
                            PlaceBook.builder()
                                    .place(place)
                                    .book(book)
                                    .build()
                    );
                }
            });
        }
    }
}
