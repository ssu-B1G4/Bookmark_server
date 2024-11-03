package B1G4.bookmark.converter;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.web.dto.MemberDTO.AuthResponseDTO;
import B1G4.bookmark.web.dto.MemberDTO.MemberResponseDTO;

public class MemberConverter {
    public static MemberResponseDTO.MyPageResponse toMyPageResponse(Member member) {
        return MemberResponseDTO.MyPageResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
