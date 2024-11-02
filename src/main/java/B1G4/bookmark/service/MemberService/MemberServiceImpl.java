package B1G4.bookmark.service.MemberService;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.UserException;
import B1G4.bookmark.converter.AuthConverter;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.redis.service.RefreshTokenService;
import B1G4.bookmark.repository.MemberRepository;
import B1G4.bookmark.repository.UserPlaceRepository;
import B1G4.bookmark.security.jwt.JwtTokenProvider;
import B1G4.bookmark.security.provider.KakaoAuthProvider;
import B1G4.bookmark.web.dto.MemberDTO.AuthResponseDTO;
import B1G4.bookmark.web.dto.authDTO.KakaoProfile;
import B1G4.bookmark.web.dto.authDTO.OAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final UserPlaceRepository userPlaceRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final KakaoAuthProvider kakaoAuthProvider;

    @Override
    public Boolean isSaved(Member member, Place place) {
        return userPlaceRepository.existsUserPlaceByMemberAndPlace(member, place);
    }

    @Override
    public Member findMemberById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public AuthResponseDTO.OAuthResponse kakaoLogin(String code) {
        OAuthToken oAuthToken = kakaoAuthProvider.requestToken(code);
        KakaoProfile kakaoProfile =
                kakaoAuthProvider.requestKakaoProfile(oAuthToken.getAccess_token());

        // 유저 정보 받기
        Optional<Member> queryMember =
                memberRepository.findByEmail(
                        kakaoProfile.getKakaoAccount().getEmail());

        // 가입자 혹은 비가입자 체크해서 로그인 처리
        if (queryMember.isPresent()) {
            Member member = queryMember.get();
            String accessToken = jwtTokenProvider.createAccessToken(member.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());
        //    refreshTokenService.saveToken(refreshToken);
            return AuthConverter.toOAuthResponse(accessToken, refreshToken, true, member);
        } else {
            Member member = memberRepository.save(AuthConverter.toMember(kakaoProfile));
            String accessToken = jwtTokenProvider.createAccessToken(member.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());
        //    refreshTokenService.saveToken(refreshToken);
            return AuthConverter.toOAuthResponse(accessToken, refreshToken, false, member);
        }
    }

}
