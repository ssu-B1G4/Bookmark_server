package B1G4.bookmark.domain.enums;

import lombok.Getter;

@Getter
public enum Noise {
    Low("낮음"),
    Medium("중간"),
    High("높음");

    private final String noise;

    Noise(String noise){
        this.noise = noise;
    }
}
