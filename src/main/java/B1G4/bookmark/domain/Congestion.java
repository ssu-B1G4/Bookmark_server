package B1G4.bookmark.domain;

import B1G4.bookmark.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Congestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer zero;
    private Integer one;
    private Integer two;
    private Integer three;
    private Integer four;
    private Integer five;
    private Integer six;
    private Integer seven;
    private Integer eight;
    private Integer nine;
    private Integer ten;
    private Integer eleven;
    private Integer twelve;
    private Integer thirteen;
    private Integer fourteen;
    private Integer fifteen;
    private Integer sixteen;
    private Integer seventeen;
    private Integer eighteen;
    private Integer nineteen;
    private Integer twenty;
    private Integer twentyOne;
    private Integer twentyTwo;
    private Integer twentyThree;
    private Integer twentyFour;
}
