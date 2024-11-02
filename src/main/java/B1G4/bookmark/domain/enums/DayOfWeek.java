package B1G4.bookmark.domain.enums;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.handler.PlaceHandler;

public enum DayOfWeek {
    Monday("월요일"),
    Tuesday("화요일"),
    Wednesday("수요일"),
    Thursday("목요일"),
    Friday("금요일"),
    Saturday("토요일"),
    Sunday("일요일");
    private final String viewName;
    DayOfWeek(String dayOfWeek) {this.viewName = dayOfWeek;}
    public String getViewName() {
        return viewName;
    }

    public static DayOfWeek toDayOfWeek(String name) {
        if(name == null || name.isEmpty()) return null;
        for(DayOfWeek dayEnum : DayOfWeek.values()) {
            if(dayEnum.getViewName().equals(name)) {
                return dayEnum;
            }
        }
        throw new PlaceHandler(ErrorStatus.INVALID_DAY);
    }
}
