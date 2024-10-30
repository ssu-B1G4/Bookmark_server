package B1G4.bookmark.domain.enums;

import lombok.Getter;

@Getter
public enum Wifi {
    // 2종류만 있는건가
    On("있음"),
    Off("없음");

    private final String wifi;

    Wifi(String wifi) {
        this.wifi = wifi;
    }

    public String getViewName() {
        return wifi;
    }
}
