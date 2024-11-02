package B1G4.bookmark.domain.enums;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.handler.PlaceHandler;
import lombok.Getter;

@Getter
public enum Wifi {
    // 2종류만 있는건가
    On("있어요"),
    Off("없어요");

    private final String wifi;

    Wifi(String wifi) {
        this.wifi = wifi;
    }

    public String getViewName() {
        return wifi;
    }
    public static Wifi toWifi(String name) {
        for(Wifi wifiEnum : Wifi.values()) {
            if(wifiEnum.getViewName().contains(name)) {
                return wifiEnum;
            }
        }
        throw new PlaceHandler(ErrorStatus.WIFI_NOT_FOUND);
    }
}
