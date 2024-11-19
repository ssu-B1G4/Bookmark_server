package B1G4.bookmark.domain;

import B1G4.bookmark.domain.common.BaseEntity;
import B1G4.bookmark.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Enumerated(EnumType.STRING)
    private Category category;

    private String phone;
    private String url;
    private String summary;

    private Double latitude;
    private Double longitude;
    @Builder.Default
    private Integer reviewCount = 0;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceImg> placeImgList = new ArrayList<>();
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<OperatingTime> placeTimeList = new ArrayList<>();

    @OneToOne
    private Congestion congestion;

    // 리뷰 개수 + 1
    public void addReviewCount() {
        if (this.reviewCount == null) this.reviewCount = 0;
        this.reviewCount++;
    }

    // 분위기 빈도수 저장 (각 분위기가 얼마나 선택되었는지)
    @ElementCollection
    @CollectionTable(name = "place_mood_frequency", joinColumns = @JoinColumn(name = "place_id"))
    @MapKeyColumn(name = "mood")
    @Column(name = "frequency")
    @Builder.Default
    private Map<Mood, Integer> moodFrequency = new HashMap<>();

    public void incrementMoodCount(Mood mood) {
        moodFrequency.put(mood, moodFrequency.getOrDefault(mood, 0) + 1);
    }

    public void decrementMoodCount(Mood mood) { // 리뷰 삭제 할 때
        moodFrequency.put(mood, moodFrequency.getOrDefault(mood, 0) - 1);
    }

    public void updateMoods() {
        List<Map.Entry<Mood, Integer>> topMoods = moodFrequency.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(2)
                .collect(Collectors.toList());

        this.mood1 = topMoods.size() > 0 ? topMoods.get(0).getKey() : null;
        this.mood2 = topMoods.size() > 1 ? topMoods.get(1).getKey() : null;
    }

    public void setCongestion(Congestion congestion) {
        this.congestion = congestion;
    }
}
