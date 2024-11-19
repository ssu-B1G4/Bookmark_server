package B1G4.bookmark.converter;

import B1G4.bookmark.domain.OperatingTime;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.enums.DayOfWeek;

public class OperatingTimeConverter {
    public static OperatingTime toOperatingTime(Place place, DayOfWeek dayOfWeek) {
        return OperatingTime.builder()
                .place(place)
                .openTime(null)
                .closeTime(null)
                .isOff(false)
                .dayOfWeek(dayOfWeek)
                .build();
    }
}
