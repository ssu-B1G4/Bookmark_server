package B1G4.bookmark.domain.enums;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.handler.PlaceHandler;
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
    public static Category toCategory(String name) {
        if(name == null || name.isEmpty()) return null;
        for(Category categoryEnum : Category.values()) {
            if(categoryEnum.getViewName().contains(name)) {
                return categoryEnum;
            }
        }
        throw new PlaceHandler(ErrorStatus.CATEGORY_NOT_FOUND);
    }
}