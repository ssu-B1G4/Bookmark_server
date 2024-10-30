package B1G4.bookmark.domain.enums;

import lombok.Getter;

@Getter
public enum Category {
    Indoor("실내 공간"),
    Outdoor("야외 공간");

    private final String category;

    private Category(String category) {
        this.category = category;
    }
    public String getViewName() {
        return category;
    }
}
