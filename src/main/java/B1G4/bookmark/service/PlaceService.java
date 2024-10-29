package B1G4.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PlaceService {
    @Value("${NAVER_MAP_URL}")
    private String apiUrl;
    @Value("${NAVER_CLIENT_ID}")
    private String clientId;
    @Value("${NAVER_CLIENT_SECRET}")
    private String clientSecret;
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
}