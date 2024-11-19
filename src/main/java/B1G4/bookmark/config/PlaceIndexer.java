package B1G4.bookmark.config;

import B1G4.bookmark.document.PlaceDocument;
import B1G4.bookmark.repository.PlaceRepository;
import B1G4.bookmark.repository.PlaceSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaceIndexer {
    private final PlaceRepository placeRepository;
    private final PlaceSearchRepository placeSearchRepository;

    @PostConstruct
    public void indexPlaces() {
        log.info("Elasticsearch 데이터 indexing 시작");
        try {
            placeRepository.findAll().forEach(place -> {
                placeSearchRepository.save(PlaceDocument.from(place));
            });
            log.info("Elasticsearch 데이터 indexing 완료");
        } catch (Exception e) {
            log.error("Elasticsearch indexing 실패: {}", e.getMessage(), e);
        }
    }
}
