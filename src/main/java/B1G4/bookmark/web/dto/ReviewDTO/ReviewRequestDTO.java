package B1G4.bookmark.web.dto.ReviewDTO;

import B1G4.bookmark.domain.enums.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewRequestDTO {
    private String content;
    private LocalDateTime writtenDate;
    private LocalDateTime visitDate;
    private Outlet outlet;
    private Size size;
    private Wifi wifi;
    private Noise noise;

    private List<Mood> moods;
    private List<BookDTO> books;
}
