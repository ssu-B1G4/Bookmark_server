package B1G4.bookmark.domain.enums;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.handler.PlaceHandler;
import lombok.Getter;

@Getter
public enum Mood {
    Comfortable("🎆 편안한"),
    Exciting("🎉 신나는"),
    Calm("🌌 차분한"),
    Joyful("✨ 즐거운"),
    cozy("🪑 아늑한"),
    interesting("🍀 재미있는");

    private final String mood;

    Mood(String mood) {
        this.mood = mood;
    }
    public String getViewName() {
        return mood;
    }
    public static Mood toMood(String name) {
        if(name == null || name.isEmpty()) return null;
        for(Mood moodEnum : Mood.values()) {
            if(moodEnum.getViewName().contains(name)) {
                return moodEnum;
            }
        }
        throw new PlaceHandler(ErrorStatus.MOOD_NOT_FOUND);
    }
}

