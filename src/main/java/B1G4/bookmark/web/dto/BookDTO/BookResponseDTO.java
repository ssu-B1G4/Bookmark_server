package B1G4.bookmark.web.dto.BookDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

public class BookResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookPreviewDTO {
        private Long bookId;
        private String title;
        private String author;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookListDTO {
        private List<BookPreviewDTO> books;
        private Integer totalPages;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;

        public static BookListDTO from(Page<BookPreviewDTO> bookPage) {
            return BookListDTO.builder()
                    .books(bookPage.getContent())
                    .totalPages(bookPage.getTotalPages())
                    .totalElements(bookPage.getTotalElements())
                    .isFirst(bookPage.isFirst())
                    .isLast(bookPage.isLast())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookSearchDTO {
        private List<BookPreviewDTO> books;
        private int totalBooks;
    }
}
