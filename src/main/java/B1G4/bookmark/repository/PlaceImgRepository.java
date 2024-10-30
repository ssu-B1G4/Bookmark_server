package B1G4.bookmark.repository;

import B1G4.bookmark.domain.Place;
import B1G4.bookmark.domain.PlaceImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceImgRepository extends JpaRepository<PlaceImg, Long> {
    @Query("select pi.url from PlaceImg pi where pi.place = :place")
    List<String> findAllUrlByPlace(Place place);
}
