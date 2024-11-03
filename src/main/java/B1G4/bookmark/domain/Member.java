package B1G4.bookmark.domain;

import B1G4.bookmark.domain.common.BaseEntity;
import B1G4.bookmark.domain.enums.Mood;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String accessToken;
    private String refreshToken;
    private String email;

    @ColumnDefault("0")  //삭제시 1
    private int isDelete;

    // 선호 분위기 1개만 저장?
    @Enumerated(EnumType.STRING)
    private Mood mood;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    // 리뷰 추가
    public void addReview(Review review) {
        reviews.add(review);
        review.setMember(this);
    }

    // 리뷰 제거
    public void removeReview(Review review) {
        reviews.remove(review);
        review.setMember(null);
    }

    public void updateMood(Mood mood) {
        this.mood = mood;
    }

    @Builder
    public Member(String nickname, Mood mood, String email) {
        this.nickname = nickname;
        this.mood = mood;
        this.email = email;
    }

    public void updateToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void updateIsDelete(Integer integer){
        this.isDelete = integer;
    }


}
