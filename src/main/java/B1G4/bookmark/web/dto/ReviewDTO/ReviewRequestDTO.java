package B1G4.bookmark.web.dto.ReviewDTO;

import B1G4.bookmark.domain.enums.*;
import B1G4.bookmark.web.dto.BookDTO.BookDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class ReviewRequestDTO {
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime writtenDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime visitDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime visitStartTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime visitEndTime;
    private int congestion;
    private Outlet outlet;
    private Size size;
    private Wifi wifi;
    private Noise noise;

    private List<Mood> moods;
    private List<BookDTO> books;
}
