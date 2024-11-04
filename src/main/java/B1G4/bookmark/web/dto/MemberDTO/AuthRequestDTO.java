package B1G4.bookmark.web.dto.MemberDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class AuthRequestDTO {
    @Getter
    public static class RefreshTokenDTO {
        @JsonProperty("refresh_token")
        String refreshToken;
    }
}
