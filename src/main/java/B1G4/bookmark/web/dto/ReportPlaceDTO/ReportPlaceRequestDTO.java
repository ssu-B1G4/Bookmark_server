package B1G4.bookmark.web.dto.ReportPlaceDTO;

import B1G4.bookmark.domain.enums.*;
import B1G4.bookmark.web.dto.BookDTO.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportPlaceRequestDTO {
    private String name;
    private String address;
    private String content;
    private List<Mood> moods;
    private Wifi wifi;
    private Outlet outlet;
    private Size size;
    private Noise noise;
    private Category category;
    private String title;
    private String author;
    private List<MultipartFile> images;
}