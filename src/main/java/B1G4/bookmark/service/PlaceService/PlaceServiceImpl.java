package B1G4.bookmark.service.PlaceService;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.UserException;
import B1G4.bookmark.apiPayload.exception.handler.PlaceHandler;
import B1G4.bookmark.converter.CongestionConverter;
import B1G4.bookmark.converter.OperatingTimeConverter;
import B1G4.bookmark.converter.PlaceConverter;
import B1G4.bookmark.document.PlaceDocument;
import B1G4.bookmark.domain.*;
import B1G4.bookmark.domain.enums.*;
import B1G4.bookmark.repository.*;
import B1G4.bookmark.service.MemberService.MemberServiceImpl;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceRequestDTO;
import B1G4.bookmark.web.dto.PlaceDTO.PlaceResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService{
    @Value("${NAVER_MAP_URL}")
    private String apiUrl;
    @Value("${NAVER_CLIENT_ID}")
    private String clientId;
    @Value("${NAVER_CLIENT_SECRET}")
    private String clientSecret;

    private final PlaceRepository placeRepository;
    private final MemberServiceImpl memberService;
    private final PlaceImgRepository placeImgRepository;
    private final OperatingTimeRepository operatingTimeRepository;
    private final UserPlaceRepository userPlaceRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final PlaceSearchRepository placeSearchRepository;
    private final CongestionRepository congestionRepository;
    private static final Double EARTH_RADIUS = 6371.0;

    @Override
    public Map<String, Double> getGeoData(String address) {
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
            headers.set("X-NCP-APIGW-API-KEY", clientSecret);
            headers.set("ACCEPT", "application/json");
            String fullUrl = apiUrl + "?query=" + address;
            HttpEntity<String> http = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.GET,
                    http,
                    Map.class
            );
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Double> result = new HashMap<>();
                Map<String, Object> responseBody = response.getBody();

                List<Map<String, Object>> addresses = (List<Map<String, Object>>) responseBody.get("addresses");
                if (!addresses.isEmpty()) {
                    Map<String, Object> firstAddress = addresses.get(0);
                    // Convert String coordinates to Double
                    String xStr = String.valueOf(firstAddress.get("x"));
                    String yStr = String.valueOf(firstAddress.get("y"));

                    try {
                        result.put("longitude", Double.parseDouble(xStr));
                        result.put("latitude", Double.parseDouble(yStr));
                        return result;
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid coordinate format received from API");
                    }
                }
            }
            throw new RuntimeException("Failed to get geocoding data");
        }catch (Exception e) {
            throw new RuntimeException("Error :" + e.getMessage());
        }
    }
    @Override
    @Transactional
    public Place createPlace(PlaceRequestDTO.PlaceCreateDTO request, Double longitude, Double latitude) {
        Place place = PlaceConverter.toPlace(request, longitude, latitude);
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            OperatingTime operatingTime = OperatingTimeConverter.toOperatingTime(place, dayOfWeek);
            operatingTimeRepository.save(operatingTime);
        }
        Congestion congestion = CongestionConverter.toCongestion(place);
        place.setCongestion(congestion);
        placeRepository.save(place);
        placeSearchRepository.save(PlaceDocument.from(place));
        congestionRepository.save(congestion);
        return place;
    }

    @Override
    public PlaceResponseDTO.PlacePreviewDTO previewPlace(Long placeId, Member member) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(()-> new EntityNotFoundException("Place가 없습니다."));
        Boolean isSaved = memberService.isSaved(member, place);
        List<String> placeImgList = placeImgRepository.findAllUrlByPlace(place);
        PlaceResponseDTO.PlacePreviewDTO response = PlaceConverter.toPlacePreviewDTO(place, isSaved, placeImgList);
        return response;
    }

    @Override
    public PlaceResponseDTO.PlaceDetailDTO detailPlace(Long placeId, Member member) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(()-> new EntityNotFoundException("Place가 없습니다."));
        Boolean isSaved = memberService.isSaved(member, place);
        List<String> placeImgList = placeImgRepository.findAllUrlByPlace(place);
        Map<String, Map<String, String>> operatingTimeList = getOperatingTime(place);
        PlaceResponseDTO.PlaceDetailDTO response = PlaceConverter.toPlaceDetailDTO(place, isSaved, placeImgList, operatingTimeList);
        System.out.println("operatingTimeList = " + operatingTimeList);
        return response;
    }

    @Override
    public Page<Place> findNearbyPlaces(Double longitude, Double latitude, Double radius, Integer page) {
        PageRequest pageRequest = PageRequest.of(page-1, 10);
        double latRange = radius / EARTH_RADIUS * (180 / Math.PI);
        double lonRange = radius / (EARTH_RADIUS * Math.cos(Math.toRadians(latitude))) * (180 / Math.PI);
        double minLat = latitude - latRange;
        double maxLat = latitude + latRange;
        double minLng = longitude - lonRange;
        double maxLng = longitude + lonRange;

        //반경 내 조회
        return placeRepository.findByBoundingBox(minLat, maxLat, minLng, maxLng,
                latitude, longitude, radius, pageRequest);

    }
    @Override
    public Page<Place> searchPlaces(String search, Integer page) {
        PageRequest pageRequest = PageRequest.of(page-1, 10);
            /* 기존 Jpa 검색
        Page<Place> searchPlaces = placeRepository.findByNameContainingOrAddressContaining(search, pageRequest);
        return searchPlaces;
            기존 Jpa 검색 */

        /*elastic search 검색*/
        /*자동완성 + 한영오타보정 + 초성 검색*/
        // 검색어가 비어있는 경우 처리
        if (search == null || search.trim().isEmpty()) {
            return Page.empty(pageRequest);
        }

        try {
            // 검색 쿼리 생성
            Query searchQuery = NativeQuery.builder()
                    .withQuery(q -> q
                            .bool(b -> b
                                    .should(s -> s
                                            .match(m -> m
                                                    .field("name")
                                                    .query(search)
                                            )
                                    )
                                    .should(s -> s
                                            .match(m -> m
                                                    .field("address")
                                                    .query(search)
                                            )
                                    )
                            )
                    )
                    .withPageable(pageRequest)
                    .build();

            // Elasticsearch 검색 실행
            SearchHits<PlaceDocument> searchHits = elasticsearchOperations.search(
                    searchQuery,
                    PlaceDocument.class
            );

            // 검색 결과를 Place 엔티티로 변환
            List<Place> placeList = searchHits.stream()
                    .map(hit -> placeRepository.findById(hit.getContent().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Place not found with id: " + hit.getContent().getId())))
                    .collect(Collectors.toList());

            // Page 객체 생성 및 반환
            return new PageImpl<>(placeList, pageRequest, searchHits.getTotalHits());

        } catch (Exception e) {
            // 에러 발생 시 JPA로 폴백
            return placeRepository.findByNameContainingOrAddressContaining(search, pageRequest);
        }
    }

    //필터 적용
    @Override
    public Page<Place> addFilter(Page<Place> places, String mood, String day, String time, String size, String outlet, String noise, String wifi) {
        Stream<Place> filteredStream = places.getContent().stream();
        //분위기 필터 적용
        if(mood != null && !mood.isEmpty()){
            filteredStream = filteredStream.filter(place ->
                place.getMood1().equals(Mood.toMood(mood)) ||
                place.getMood2().equals(Mood.toMood(mood)));
        }
        //운영시간 필터 적용
        if(day != null && !day.isEmpty() && time != null && !time.isEmpty()){
            filteredStream = filteredStream.filter(place ->
                    isOpen(place, time, day)
                    );
        }
        else if(day != null || time != null)
            throw new PlaceHandler(ErrorStatus.INVALID_TIME_FILTER);
        //공간 크기 필터
        if(size != null && !size.isEmpty()){
            filteredStream = filteredStream.filter(place ->
                    place.getSize().equals(Size.toSize(size)));
        }
        //콘센트 필터
        if(outlet != null && !outlet.isEmpty()){
            filteredStream = filteredStream.filter(place ->
                    place.getOutlet().equals(Outlet.toOutlet(outlet)));
        }
        //소음 필터
        if(noise != null && !noise.isEmpty()){
            filteredStream = filteredStream.filter(place ->
                    place.getNoise().equals(Noise.toNoise(noise)));
        }
        //와이파이 필터
        if(wifi != null && !wifi.isEmpty()){
            filteredStream = filteredStream.filter(place ->
                    place.getWifi().equals(Wifi.toWifi(wifi)));
        }
        List<Place> filteredList = filteredStream.collect(Collectors.toList());
        return new PageImpl<>(filteredList, places.getPageable(), filteredList.size());

    }

    //공간의 운영시간을 조회하는 서비스
    @Override
    public Map<String, Map<String, String>> getOperatingTime(Place place) {
        List<OperatingTime> operatingTimeList = operatingTimeRepository.findAllByPlace(place);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        //요일 순서
        List<String> dayOrder = Arrays.asList("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일");
        //요일 순서 유지를 위해 LinkedHashMap 사용
        Map<String, Map<String, String>> result = new LinkedHashMap<>();
        //요일 별 빈 map 생성
        dayOrder.forEach(day -> result.put(day, new HashMap<>()));
        operatingTimeList.stream().forEach(operatingTime -> {
            Map<String, String> timeMap = new HashMap<>();
            if(!operatingTime.getIsOff()){
                timeMap.put("openTime", operatingTime.getOpenTime().format(formatter));
                timeMap.put("closeTime", operatingTime.getCloseTime().format(formatter));
            }
            result.put(operatingTime.getDayOfWeek().getViewName(),timeMap);
        });
        return result;
    }


    //공간이 해당 요일, 시간에 운영중인지 여부를 확인하는 서비스
    @Override
    public Boolean isOpen(Place place, String filterTime, String dayOfWeek) {
        DayOfWeek day = DayOfWeek.toDayOfWeek(dayOfWeek);
        LocalTime time = LocalTime.parse(filterTime);
        OperatingTime operatingTime = operatingTimeRepository.findByPlaceAndDayOfWeek(place, day);
        //TODO : 운영시간 다 채우기
        if(operatingTime == null) {
            return false;
        }
        if(operatingTime.getIsOff()) {
            return false;
        }
        else{
            LocalTime open = operatingTime.getOpenTime();
            LocalTime close = operatingTime.getCloseTime();
            if (open.isBefore(close)) {  // 일반적인 경우 (예: 09:00 ~ 18:00)
                return !time.isBefore(open) && !time.isAfter(close);
            } else {  // 자정을 넘어가는 경우 (예: 22:00 ~ 02:00)
                return !time.isBefore(open) || !time.isAfter(close);
            }
        }
    }

    @Override
    public void bookmarkPlace(Member member, Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new UserException(ErrorStatus.PLACE_NOT_FOUND));

        if (userPlaceRepository.findByMemberAndPlace(member, place).isEmpty()) {
            userPlaceRepository.save(PlaceConverter.toUserPlace(member, place));
        } else throw new UserException(ErrorStatus.BOOKMARK_FAILED);
    }

    @Override
    public void unbookmarkPlace(Member member, Long placeId){
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new UserException(ErrorStatus.PLACE_NOT_FOUND));

        UserPlace userPlace = userPlaceRepository.findByMemberAndPlace(member, place)
                .orElseThrow(() -> new UserException(ErrorStatus.USERPLACE_NOT_FOUND));

        try {
            userPlaceRepository.delete(userPlace);
        }catch (Exception e){
            throw new UserException(ErrorStatus.UNBOOKMARK_FAILED);
        }
    }

    @Override
    public PlaceResponseDTO.BookmarkPlaceListDTO getBookmarkPlaceList(Member member, int page){
        PageRequest pageRequest = PageRequest.of((page-1), 10);
        Page<UserPlace> userPlaces = userPlaceRepository.findByMember(member, pageRequest);

        return PlaceConverter.toBookmarkPlaceList(userPlaces);
    }

    public PlaceResponseDTO.PlacePreviewListDTO getRecommendedPlaces(Member member, int page) {
        PageRequest pageable = PageRequest.of((page-1), 10);

        // 사용자의 선호 분위기와 일치하는 장소를 조회
        Page<Place> places = placeRepository.findByMood(pageable, member.getMood());
        // `PlaceConverter`를 사용하여 `PlacePreviewListDTO`로 변환
        return PlaceConverter.toPlacePreviewList(places, member, memberService, placeImgRepository);
    }

    @Override
    public PlaceResponseDTO.BookMarkByPlaceDTO getBookmarkByPlaceId(Member member, Long placeId){
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_NOT_FOUND));
        if(userPlaceRepository.findByMemberAndPlace(member, place).isPresent()){
            return PlaceConverter.toBookmarkByPlaceDTO(place, true);
        }else
            return PlaceConverter.toBookmarkByPlaceDTO(place, false);
    }
}