package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.service.BookService.BookServiceImpl;
import B1G4.bookmark.web.dto.BookDTO.BookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(
            summary = "특정 장소의 도서 목록 조회",
            description = "특정 장소에 있는 도서 목록을 페이지네이션 형식으로 조회합니다. " +
                          "페이지 번호와 페이지 크기를 설정하여 원하는 범위의 도서를 가져올 수 있습니다."
    )
    @GetMapping("/books/{placeId}")
    public BaseResponse<BookResponseDTO.BookListDTO> getBooksByPlace(
            @Parameter(description = "도서를 조회할 장소 ID", required = true)
            @PathVariable Long placeId,

            @Parameter(description = "페이지 번호 (기본값: 0)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "페이지당 항목 수 (기본값: 10)")
            @RequestParam(defaultValue = "10") int size) {

        Page<BookResponseDTO.BookPreviewDTO> books = bookService.getBooksByPlace(placeId, page, size);

        BookResponseDTO.BookListDTO responseDTO = BookResponseDTO.BookListDTO.from(books);

        return BaseResponse.of(SuccessStatus.BOOK_FETCH_OK, responseDTO);
    }
}
