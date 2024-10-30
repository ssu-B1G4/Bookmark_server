package B1G4.bookmark.web.dto.ReviewDTO;

import B1G4.bookmark.domain.enums.Noise;
import B1G4.bookmark.domain.enums.Outlet;
import B1G4.bookmark.domain.enums.Size;
import B1G4.bookmark.domain.enums.Wifi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReviewRequestDTO {
    private String content;
    private LocalDateTime writtenDate;
    private LocalDateTime visitDate;
    private Outlet outlet;
    private Size size;
    private Wifi wifi;
    private Noise noise;
}
