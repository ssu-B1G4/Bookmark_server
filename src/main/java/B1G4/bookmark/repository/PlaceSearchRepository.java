package B1G4.bookmark.repository;

import B1G4.bookmark.document.PlaceDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceSearchRepository extends ElasticsearchRepository<PlaceDocument, Long> {
    Page<PlaceDocument> findByNameOrAddress(String name, String address, Pageable pageable);
}
