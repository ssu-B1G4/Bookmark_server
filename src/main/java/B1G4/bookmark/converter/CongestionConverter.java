package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Congestion;
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

    private static float getCongestionLevel(Congestion congestion, int hour) {
        return switch (hour) {
            case 0 -> congestion.getZero();
            case 1 -> congestion.getOne();
            case 2 -> congestion.getTwo();
            case 3 -> congestion.getThree();
            case 4 -> congestion.getFour();
            case 5 -> congestion.getFive();
            case 6 -> congestion.getSix();
            case 7 -> congestion.getSeven();
            case 8 -> congestion.getEight();
            case 9 -> congestion.getNine();
            case 10 -> congestion.getTen();
            case 11 -> congestion.getEleven();
            case 12 -> congestion.getTwelve();
            case 13 -> congestion.getThirteen();
            case 14 -> congestion.getFourteen();
            case 15 -> congestion.getFifteen();
            case 16 -> congestion.getSixteen();
            case 17 -> congestion.getSeventeen();
            case 18 -> congestion.getEighteen();
            case 19 -> congestion.getNineteen();
            case 20 -> congestion.getTwenty();
            case 21 -> congestion.getTwentyOne();
            case 22 -> congestion.getTwentyTwo();
            case 23 -> congestion.getTwentyThree();
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
}
