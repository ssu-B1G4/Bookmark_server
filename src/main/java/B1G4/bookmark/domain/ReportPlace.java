package B1G4.bookmark.domain;

import B1G4.bookmark.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ReportPlace {

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

    @Enumerated(EnumType.STRING)
    private Mood mood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
