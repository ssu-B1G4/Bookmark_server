package B1G4.bookmark.service.MemberService;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.apiPayload.exception.AuthException;
import B1G4.bookmark.apiPayload.exception.UserException;
import B1G4.bookmark.converter.AuthConverter;
import B1G4.bookmark.converter.MemberConverter;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.redis.service.RefreshTokenService;
import B1G4.bookmark.repository.MemberRepository;
import B1G4.bookmark.repository.UserPlaceRepository;
import B1G4.bookmark.security.provider.JwtTokenProvider;
import B1G4.bookmark.security.provider.KakaoAuthProvider;
import B1G4.bookmark.web.dto.MemberDTO.AuthResponseDTO;
import B1G4.bookmark.web.dto.MemberDTO.MemberResponseDTO;
import B1G4.bookmark.web.dto.authDTO.KakaoProfile;
import B1G4.bookmark.web.dto.authDTO.OAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        OAuthToken oAuthToken;
        try {
            oAuthToken = kakaoAuthProvider.requestToken(code);
        } catch (Exception e){
            throw new UserException(ErrorStatus.AUTH_INVALID_CODE);
        }

        KakaoProfile kakaoProfile;
        try {
            kakaoProfile =
                    kakaoAuthProvider.requestKakaoProfile(oAuthToken.getAccess_token());
        }catch (Exception e){
            throw new UserException(ErrorStatus.INVALID_REQUEST_INFO);
        }

        // 유저 정보 받기
        Optional<Member> queryMember =
                memberRepository.findByEmail(
                        kakaoProfile.getKakaoAccount().getEmail());

        // 가입자 혹은 비가입자 체크해서 로그인 처리
        if (queryMember.isPresent()) {
            Member member = queryMember.get();
            // 회원탈퇴 여부 검증
            if(member.getIsDelete()==1){
                throw new UserException(ErrorStatus.USER_NOT_FOUND);
            } else {
                String accessToken = jwtTokenProvider.createAccessToken(member.getId());
                String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());
                member.updateToken(accessToken, refreshToken);
                memberRepository.save(member);
                //    refreshTokenService.saveToken(refreshToken);  redis관련
                return AuthConverter.toOAuthResponse(accessToken, refreshToken, member);
            }
        } else {
            Member member = memberRepository.save(AuthConverter.toMember(kakaoProfile, makeNickname()));
            String accessToken = jwtTokenProvider.createAccessToken(member.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());
            member.updateToken(accessToken, refreshToken);
            memberRepository.save(member);
        //    refreshTokenService.saveToken(refreshToken);
            return AuthConverter.toOAuthResponse(accessToken, refreshToken, member);
        }
    }

    @Transactional
    @Override
    public void deleteMember(Member member) {
        try {
            member.updateIsDelete(1);
            memberRepository.save(member);
        } catch (Exception e) {
            throw new UserException(ErrorStatus.USER_DELETE_FAILED);
        }
    }

    @Override
    @Transactional
    public AuthResponseDTO.TokenRefreshResponse refresh(String refreshToken) {
        jwtTokenProvider.isTokenValid(refreshToken);
        Long id = jwtTokenProvider.getId(refreshToken);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        String newAccessToken =
                jwtTokenProvider.createAccessToken(id);
        String newRefreshToken =
                jwtTokenProvider.createRefreshToken(id);
        member.updateToken(newAccessToken, newRefreshToken);
        memberRepository.save(member);
        // refreshTokenService.saveToken(newRefreshToken);
        return AuthConverter.toTokenRefreshResponse(newAccessToken, newRefreshToken);
    }


    @Override
    public MemberResponseDTO.MyPageResponse getMyPageInfo(Member member) {
        return MemberConverter.toMyPageResponse(member);
    }


    // 랜덤 닉네임 생성
    private String makeNickname(){
        List<String> determiners = List.of(
                "예쁜", "멋진", "귀여운", "배고픈", "철학적인", "현학적인", "슬픈", "파란", "비싼", "밝은", "생각하는", "하얀"
        );

        List<String> animals = List.of(
                "토끼", "비버", "강아지", "부엉이", "여우", "호랑이", "문어", "고양이", "미어캣", "다람쥐", "수달", "곰"
        );

        Random random = new Random();
        String determiner = determiners.get(random.nextInt(determiners.size()));
        String animal = animals.get(random.nextInt(animals.size()));
        return determiner + " " + animal;
    }

}
