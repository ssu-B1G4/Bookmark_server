package B1G4.bookmark.domain.enums;

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
}
