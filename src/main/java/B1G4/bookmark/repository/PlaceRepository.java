package B1G4.bookmark.repository;

import B1G4.bookmark.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query(value = """
   SELECT p.*, (6371 * acos(
       cos(radians(:latitude)) * 
       cos(radians(p.latitude)) * 
       cos(radians(p.longitude) - radians(:longitude)) + 
       sin(radians(:latitude)) * 
       sin(radians(p.latitude))
   )) as distance 
   FROM Place p
   WHERE p.latitude BETWEEN :minLat AND :maxLat 
   AND p.longitude BETWEEN :minLng AND :maxLng
   HAVING distance <= :radius
   ORDER BY distance
   """, nativeQuery = true)
    Page<Place> findByBoundingBox(
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLng") double minLng,
            @Param("maxLng") double maxLng,
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius,
            PageRequest pageRequest
    );

    @Query("SELECT p FROM Place p WHERE" +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))" +
            "OR (LOWER(p.address) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Place> findByNameContainingOrAddressContaining(@Param("search") String search, PageRequest pageRequest);
}
