package B1G4.bookmark.domain.enums;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.handler.PlaceHandler;
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
    public static Noise toNoise(String name) {
        if(name == null || name.isEmpty()) return null;
        for(Noise noiseEnum : Noise.values()) {
            if(noiseEnum.getViewName().contains(name)) {
                return noiseEnum;
            }
        }
        throw new PlaceHandler(ErrorStatus.NOISE_NOT_FOUND);
    }
}
