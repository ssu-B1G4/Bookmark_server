package B1G4.bookmark.web.dto.MemberDTO;

import lombok.*;

public class MemberResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MyPageResponse {
        Long memberId;
        String nickname;
    }
}
