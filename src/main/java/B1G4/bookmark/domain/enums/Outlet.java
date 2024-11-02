package B1G4.bookmark.domain.enums;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.handler.PlaceHandler;
import lombok.Getter;

@Getter
public enum Outlet {
    Few("부족"),
    Normal("보통"),
    Many("넉넉");

    private final String outlet;

    Outlet(String outlet) {
        this.outlet = outlet;
    }
    public String getViewName() {
        return outlet;
    }
    public static Outlet toOutlet(String name) {
        if(name == null || name.isEmpty()) return null;
        for(Outlet outletEnum : Outlet.values()) {
            if(outletEnum.getViewName().contains(name)) {
                return outletEnum;
            }
        }
        throw new PlaceHandler(ErrorStatus.OUTLET_NOT_FOUND);
    }
}
