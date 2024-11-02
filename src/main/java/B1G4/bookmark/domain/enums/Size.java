package B1G4.bookmark.domain.enums;

import lombok.Getter;

@Getter
public enum Size {
    Small("부족"),
    Medium("보통"),
    Large("넉넉");

    private final String size;

    Size(String size) {
        this.size = size;
    }
    public String getViewName() {
        return size;
    }
}
