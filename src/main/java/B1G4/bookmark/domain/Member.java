package B1G4.bookmark.domain;

import B1G4.bookmark.domain.enums.Mood;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nickname;
    private String img;
    private String accessToken;
    private String refreshToken;

    // 선호 분위기 1개만 저장?
    @Enumerated(EnumType.STRING)
    private Mood mood;

    public void updateMood(Mood mood) {
        this.mood = mood;
    }

    @Builder
    public Member(String name, String nickname, String img, Mood mood) {
        this.name = name;
        this.nickname = nickname;
        this.img = img;
        this.mood = mood;
    }

    public void updateToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}