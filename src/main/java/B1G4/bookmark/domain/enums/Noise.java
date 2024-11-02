package B1G4.bookmark.domain.enums;

import lombok.Getter;

@Getter
public enum Noise {
    Low("조용함"),
    Medium("보통"),
    High("생기있음");

    private final String noise;

    Noise(String noise){
        this.noise = noise;
    }
    public String getViewName() {
        return noise;
    }
}
