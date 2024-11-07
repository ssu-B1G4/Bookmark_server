package B1G4.bookmark.domain;

import B1G4.bookmark.domain.common.BaseEntity;
import B1G4.bookmark.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

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
    private String address;
    private String content;

    @Enumerated(EnumType.STRING)
    private Outlet outlet;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Wifi wifi;

    @Enumerated(EnumType.STRING)
    private Noise noise;

    @ElementCollection
    @CollectionTable(name = "report_place_mood" ,joinColumns = @JoinColumn(name = "report_place_id"))
    @Enumerated(EnumType.STRING)
    private List<Mood> moods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
