package B1G4.bookmark.web.dto.PlaceDTO;
import lombok.Getter;

public class PlaceRequestDTO {
    @Getter
    public static class PlaceCreateDTO {
        String name;
        String address;
    }
}
