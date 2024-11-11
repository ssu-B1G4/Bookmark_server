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
public class ReportPlace extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address; // 도로명 주소
    private String content;

    @Enumerated(EnumType.STRING)
    private Outlet outlet;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Wifi wifi;

    @Enumerated(EnumType.STRING)
    private Noise noise;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ElementCollection
    @CollectionTable(name = "report_place_images", joinColumns = @JoinColumn(name = "report_place_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"report_place_id", "image_url"}))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    @ElementCollection(targetClass = Mood.class)
    @CollectionTable(name = "report_place_moods", joinColumns = @JoinColumn(name = "report_place_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"report_place_id", "mood"}))
    @Enumerated(EnumType.STRING)
    @Column(name = "moods")
    private List<Mood> moods = new ArrayList<>();

    private String author;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
