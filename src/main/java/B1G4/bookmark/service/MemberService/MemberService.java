package B1G4.bookmark.service.MemberService;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.web.dto.MemberDTO.AuthResponseDTO;
import B1G4.bookmark.web.dto.MemberDTO.MemberResponseDTO;

public interface MemberService {
    Boolean isSaved(Member member, Place place);

    Member findMemberById(Long memberId);

    AuthResponseDTO.OAuthResponse kakaoLogin(String code);

    void deleteMember(Member member);

    MemberResponseDTO.MyPageResponse getMyPageInfo(Member member);

    AuthResponseDTO.TokenRefreshResponse refresh(String refreshToken);
}
