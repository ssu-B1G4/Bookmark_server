package B1G4.bookmark.domain;

import B1G4.bookmark.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@AllArgsConstructor
public class Congestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private Float zero;
    private Float one;
    private Float two;
    private Float three;
    private Float four;
    private Float five;
    private Float six;
    private Float seven;
    private Float eight;
    private Float nine;
    private Float ten;
    private Float eleven;
    private Float twelve;
    private Float thirteen;
    private Float fourteen;
    private Float fifteen;
    private Float sixteen;
    private Float seventeen;
    private Float eighteen;
    private Float nineteen;
    private Float twenty;
    private Float twentyOne;
    private Float twentyTwo;
    private Float twentyThree;
    private Float twentyFour;
}
