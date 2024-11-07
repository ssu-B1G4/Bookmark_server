package B1G4.bookmark.service.CongestionService;

import B1G4.bookmark.converter.CongestionConverter;
import B1G4.bookmark.domain.Congestion;
import B1G4.bookmark.domain.OperatingTime;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.enums.DayOfWeek;
import B1G4.bookmark.repository.CongestionRepository;
import B1G4.bookmark.repository.OperatingTimeRepository;
import B1G4.bookmark.repository.PlaceRepository;
import B1G4.bookmark.web.dto.CongestionDTO.CongestionResponseDTO;
import B1G4.bookmark.web.dto.CongestionDTO.CongestionStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CongestionServiceImpl implements CongestionService {

    private final CongestionRepository congestionRepository;
    private final PlaceRepository placeRepository;
    private final OperatingTimeRepository operatingTimeRepository;

    public CongestionResponseDTO getCongestionGraph(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공간을 찾을 수 없습니다."));

        // 오늘의 요일을 DayOfWeek enum으로 가져옴
        DayOfWeek currentDay = DayOfWeek.toDayOfWeek(
                LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
        );

        OperatingTime operatingTime = operatingTimeRepository.findByPlaceAndDayOfWeek(place, currentDay);

        LocalTime openTime = operatingTime.getOpenTime();
        LocalTime closeTime = operatingTime.getCloseTime();

        // 혼잡도 데이터 가져오기
        Congestion congestion = place.getCongestion();
        if (congestion == null) {
            throw new IllegalArgumentException("해당 공간의 혼잡도 데이터가 존재하지 않습니다.");
        }

        // 운영 시간에 맞는 혼잡도 데이터만 필터링하여 반환
        Map<String, Integer> congestionData = CongestionConverter.filterAndRoundCongestion(congestion, openTime, closeTime);

        return new CongestionResponseDTO(placeId, congestionData);
    }

    public CongestionStatusDTO getCurrentCongestionStatus(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공간을 찾을 수 없습니다."));

        Congestion congestion = place.getCongestion();
        if (congestion == null) {
            throw new IllegalArgumentException("해당 공간의 혼잡도 데이터가 존재하지 않습니다.");
        }

        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();
        Float congestionLevel = CongestionConverter.getCongestionLevel(congestion, currentHour);

        String status;
        if (congestionLevel < 30) {
            status = "여유";
        } else if (congestionLevel <= 70) {
            status = "보통";
        } else {
            status = "혼잡";
        }

        return new CongestionStatusDTO(placeId, currentHour + ":00", status);
    }
}
