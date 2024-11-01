package B1G4.bookmark.service.MemberService;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.UserException;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.repository.MemberRepository;
import B1G4.bookmark.repository.UserPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final UserPlaceRepository userPlaceRepository;
    private final MemberRepository memberRepository;
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

}
