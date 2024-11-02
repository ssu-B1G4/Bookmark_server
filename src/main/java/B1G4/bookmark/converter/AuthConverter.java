package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.web.dto.MemberDTO.AuthResponseDTO;
import B1G4.bookmark.web.dto.authDTO.KakaoProfile;

public class AuthConverter {

    public static Member toMember(KakaoProfile kakaoProfile) {
        return Member.builder()
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .build();
    }

    public static AuthResponseDTO.OAuthResponse toOAuthResponse(
            String accessToken, String refreshToken, Boolean isLogin, Member member) {
        return AuthResponseDTO.OAuthResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .isLogin(isLogin)
                .memberId(member.getId())
                .build();
    }
}
