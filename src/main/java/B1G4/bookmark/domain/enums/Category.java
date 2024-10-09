package B1G4.bookmark.domain.enums;

import lombok.Getter;

@Getter
public enum Category {
    Indoor("실내"),
    Outdoor("야외");

    private final String category;

    private Category(String category) {
        this.category = category;
    }
}
