package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.service.BookService.BookServiceImpl;
import B1G4.bookmark.web.dto.BookDTO.BookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    @GetMapping("/books/{placeId}")
    public BaseResponse<BookResponseDTO.BookListDTO> getBooksByPlace(
            @PathVariable Long placeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BookResponseDTO.BookPreviewDTO> books = bookService.getBooksByPlace(placeId, page, size);

        BookResponseDTO.BookListDTO responseDTO = BookResponseDTO.BookListDTO.from(books);

        return BaseResponse.of(SuccessStatus.BOOK_FETCH_OK, responseDTO);
    }
}
