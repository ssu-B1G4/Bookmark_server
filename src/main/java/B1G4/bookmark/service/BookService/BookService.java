package B1G4.bookmark.service.BookService;

import B1G4.bookmark.web.dto.BookDTO.BookResponseDTO;
import B1G4.bookmark.web.dto.BookDTO.BookDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    void addBooksToPlace(Long placeId, List<BookDTO> bookDTOs);
    Page<BookResponseDTO.BookPreviewDTO> getBooksByPlace(Long placeId, int page, int size);
}
