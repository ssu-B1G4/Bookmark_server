package B1G4.bookmark.document;

import B1G4.bookmark.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Id;

@Document(indexName = "jaso")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "suggest_index_analyzer", searchAnalyzer = "suggest_search_analyzer")
    private String name;

    @Field(type = FieldType.Text, analyzer = "suggest_index_analyzer", searchAnalyzer = "suggest_search_analyzer")
    private String address;

    public static PlaceDocument from(Place place) {
        return PlaceDocument.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .build();
    }
}
