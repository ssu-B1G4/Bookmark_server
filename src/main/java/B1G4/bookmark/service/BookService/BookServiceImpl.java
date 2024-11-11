package B1G4.bookmark.service.BookService;

import B1G4.bookmark.converter.BookConverter;
import B1G4.bookmark.domain.Book;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.PlaceBook;
import B1G4.bookmark.repository.BookRepository;
import B1G4.bookmark.repository.PlaceBookRepository;
import B1G4.bookmark.repository.PlaceRepository;
import B1G4.bookmark.web.dto.BookDTO.BookResponseDTO;
import B1G4.bookmark.web.dto.BookDTO.BookDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public Page<BookResponseDTO.BookPreviewDTO> getBooksByPlace(Long placeId, int page, int size) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 갖는 장소 없음"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = placeBookRepository.findBooksByPlace(place, pageable);

        return books.map(BookConverter::toBookPreviewDTO);
    }

    public BookResponseDTO.BookSearchDTO searchBooksByTitle(Long placeId, String title, int page, int size) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 장소가 없습니다."));

        Pageable pageable = PageRequest.of(page, size);
        Page<PlaceBook> placeBooks = placeBookRepository.searchByPlaceAndTitle(place, title, pageable);

        return BookResponseDTO.BookSearchDTO.builder()
                .books(placeBooks.stream()
                        .map(placeBook -> new BookResponseDTO.BookPreviewDTO(
                                placeBook.getBook().getId(),
                                placeBook.getBook().getTitle(),
                                placeBook.getBook().getAuthor()))
                        .collect(Collectors.toList()))
                .totalBooks((int) placeBooks.getTotalElements())
                .build();
    }
}
