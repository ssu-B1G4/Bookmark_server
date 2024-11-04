package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.web.dto.MemberDTO.AuthResponseDTO;
import B1G4.bookmark.web.dto.authDTO.KakaoProfile;

public class AuthConverter {

    public static Member toMember(KakaoProfile kakaoProfile, String nickname) {
        return Member.builder()
                .nickname(nickname)
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

    public static AuthResponseDTO.TokenRefreshResponse toTokenRefreshResponse(
            String accessToken, String refreshToken) {
        return AuthResponseDTO.TokenRefreshResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
