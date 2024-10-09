package B1G4.bookmark.domain;

import B1G4.bookmark.domain.common.BaseEntity;
import B1G4.bookmark.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    private Outlet outlet;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Wifi wifi;

    @Enumerated(EnumType.STRING)
    private Noise noise;

    @Enumerated(EnumType.STRING)
    private Mood mood1;

    @Enumerated(EnumType.STRING)
    private Mood mood2;

    private String phone;
    private String time;
    private String url;
    private String summary;

    private double latitude;
    private double longitude;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne
    private Congestion congestion;

    // 리뷰 추가
    public void addReview(Review review) {
        reviews.add(review);
        review.setPlace(this);
    }

    // 리뷰 제거
    public void removeReview(Review review) {
        reviews.remove(review);
        review.setPlace(null);
    }
}
