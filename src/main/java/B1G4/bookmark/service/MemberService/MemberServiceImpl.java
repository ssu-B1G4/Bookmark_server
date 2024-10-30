package B1G4.bookmark.service.MemberService;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;
import B1G4.bookmark.repository.UserPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final UserPlaceRepository userPlaceRepository;
    @Override
    public Boolean isSaved(Member member, Place place) {
        return userPlaceRepository.existsUserPlaceByMemberAndPlace(member, place);
    }
}
