package B1G4.bookmark.domain.enums;

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
}
