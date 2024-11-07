package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Book;
import B1G4.bookmark.web.dto.BookDTO.BookResponseDTO;

public class BookConverter {

    public static BookResponseDTO.BookPreviewDTO toBookPreviewDTO(Book book) {
        return BookResponseDTO.BookPreviewDTO.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
