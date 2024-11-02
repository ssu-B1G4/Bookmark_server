package B1G4.bookmark.domain.enums;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.handler.PlaceHandler;
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
    public static Size toSize(String name) {
        if(name == null || name.isEmpty()) return null;
        for(Size sizeEnum : Size.values()) {
            if(sizeEnum.getViewName().contains(name)) {
                return sizeEnum;
            }
        }
        throw new PlaceHandler(ErrorStatus.SIZE_NOT_FOUND);
    }
}
