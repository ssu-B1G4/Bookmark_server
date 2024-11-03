package B1G4.bookmark.service.BookService;

import B1G4.bookmark.domain.Place;
import B1G4.bookmark.web.dto.ReviewDTO.BookDTO;

import java.util.List;

public interface BookService {
    public void addBooksToPlace(Long placeId, List<BookDTO> bookDTOs);
}
