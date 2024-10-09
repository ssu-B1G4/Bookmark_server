package B1G4.bookmark.domain.enums;

import lombok.Getter;

@Getter
public enum Mood {
    Comfortable("편안한"),
    Exciting("신나는"),
    Calm("차분한"),
    Joyful("즐거운"),
    cozy("아늑한"),
    interesting("재미있는");

    private final String mood;

    Mood(String mood) {
        this.mood = mood;
    }
}

