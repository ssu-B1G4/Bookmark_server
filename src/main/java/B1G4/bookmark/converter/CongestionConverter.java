package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Congestion;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.web.dto.CongestionDTO.CongestionResponseDTO;

import java.time.LocalTime;
import java.util.*;

public class CongestionConverter {
    public static Congestion updateCongestion(Congestion congestion, LocalTime startTime, LocalTime endTime, float congestionLevel, int totalReviewCount) {
        int startHour = startTime.getHour();
        int endHour = endTime.getHour();
        int startMinutes = startTime.getMinute();
        int endMinutes = endTime.getMinute();

        Congestion.CongestionBuilder updatedBuilder = congestion.toBuilder();

        // 각 시간대에 혼잡도를 추가
        for (int hour = startHour; hour <= endHour; hour++) {
            float addValue = congestionLevel;

            // 1시간보다 적은 시간일 경우 혼잡도를 비율로 적용
            if (hour == startHour && startMinutes > 0) {
                addValue = congestionLevel * (60 - startMinutes) / 60f;
            } else if (hour == endHour && endMinutes > 0) {
                addValue = congestionLevel * endMinutes / 60f;
            }

            // 기존 값에 추가하고 평균값을 계산하여 적용
            float currentValue = getCongestionLevel(congestion, hour);
            float updatedValue = (currentValue * totalReviewCount + addValue) / (totalReviewCount + 1);

            // 빌더에 업데이트된 값 설정
            updatedBuilder = setCongestionLevel(updatedBuilder, hour, updatedValue);
        }

        return updatedBuilder.build();
    }

    public static float getCongestionLevel(Congestion congestion, int hour) {
        return switch (hour) {
            case 0 -> Optional.ofNullable(congestion.getZero()).orElse(0f);
            case 1 -> Optional.ofNullable(congestion.getOne()).orElse(0f);
            case 2 -> Optional.ofNullable(congestion.getTwo()).orElse(0f);
            case 3 -> Optional.ofNullable(congestion.getThree()).orElse(0f);
            case 4 -> Optional.ofNullable(congestion.getFour()).orElse(0f);
            case 5 -> Optional.ofNullable(congestion.getFive()).orElse(0f);
            case 6 -> Optional.ofNullable(congestion.getSix()).orElse(0f);
            case 7 -> Optional.ofNullable(congestion.getSeven()).orElse(0f);
            case 8 -> Optional.ofNullable(congestion.getEight()).orElse(0f);
            case 9 -> Optional.ofNullable(congestion.getNine()).orElse(0f);
            case 10 -> Optional.ofNullable(congestion.getTen()).orElse(0f);
            case 11 -> Optional.ofNullable(congestion.getEleven()).orElse(0f);
            case 12 -> Optional.ofNullable(congestion.getTwelve()).orElse(0f);
            case 13 -> Optional.ofNullable(congestion.getThirteen()).orElse(0f);
            case 14 -> Optional.ofNullable(congestion.getFourteen()).orElse(0f);
            case 15 -> Optional.ofNullable(congestion.getFifteen()).orElse(0f);
            case 16 -> Optional.ofNullable(congestion.getSixteen()).orElse(0f);
            case 17 -> Optional.ofNullable(congestion.getSeventeen()).orElse(0f);
            case 18 -> Optional.ofNullable(congestion.getEighteen()).orElse(0f);
            case 19 -> Optional.ofNullable(congestion.getNineteen()).orElse(0f);
            case 20 -> Optional.ofNullable(congestion.getTwenty()).orElse(0f);
            case 21 -> Optional.ofNullable(congestion.getTwentyOne()).orElse(0f);
            case 22 -> Optional.ofNullable(congestion.getTwentyTwo()).orElse(0f);
            case 23 -> Optional.ofNullable(congestion.getTwentyThree()).orElse(0f);
            default -> 0f;
        };
    }


    private static Congestion.CongestionBuilder setCongestionLevel(Congestion.CongestionBuilder builder, int hour, float value) {
        return switch (hour) {
            case 0 -> builder.zero(value);
            case 1 -> builder.one(value);
            case 2 -> builder.two(value);
            case 3 -> builder.three(value);
            case 4 -> builder.four(value);
            case 5 -> builder.five(value);
            case 6 -> builder.six(value);
            case 7 -> builder.seven(value);
            case 8 -> builder.eight(value);
            case 9 -> builder.nine(value);
            case 10 -> builder.ten(value);
            case 11 -> builder.eleven(value);
            case 12 -> builder.twelve(value);
            case 13 -> builder.thirteen(value);
            case 14 -> builder.fourteen(value);
            case 15 -> builder.fifteen(value);
            case 16 -> builder.sixteen(value);
            case 17 -> builder.seventeen(value);
            case 18 -> builder.eighteen(value);
            case 19 -> builder.nineteen(value);
            case 20 -> builder.twenty(value);
            case 21 -> builder.twentyOne(value);
            case 22 -> builder.twentyTwo(value);
            case 23 -> builder.twentyThree(value);
            default -> builder;
        };
    }

    public static List<CongestionResponseDTO.HourValueDTO> filterAndRoundCongestion(Congestion congestion, LocalTime openTime, LocalTime closeTime) {
        List<CongestionResponseDTO.HourValueDTO> congestionData = new ArrayList<>();

        for (int hour = openTime.getHour(); hour <= closeTime.getHour(); hour++) {
            float congestionLevel = getCongestionLevel(congestion, hour);
            int roundedValue = Math.round(congestionLevel);
            congestionData.add(new CongestionResponseDTO.HourValueDTO(hour + ":00", roundedValue));
        }

        return congestionData;
    }

    public static Congestion toCongestion(Place place) {
        return Congestion.builder()
                .place(place)
                .zero(0f)
                .one(0f)
                .two(0f)
                .three(0f)
                .four(0f)
                .five(0f)
                .six(0f)
                .seven(0f)
                .eight(0f)
                .nine(0f)
                .ten(0f)
                .eleven(0f)
                .twelve(0f)
                .thirteen(0f)
                .fourteen(0f)
                .fifteen(0f)
                .sixteen(0f)
                .seventeen(0f)
                .eighteen(0f)
                .nineteen(0f)
                .twenty(0f)
                .twentyOne(0f)
                .twentyTwo(0f)
                .twentyThree(0f)
                .twentyFour(0f)
                .build();
    }
}
