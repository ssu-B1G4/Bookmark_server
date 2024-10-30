package B1G4.bookmark.service.MemberService;

import B1G4.bookmark.domain.Member;
import B1G4.bookmark.domain.Place;

public interface MemberService {
    Boolean isSaved(Member member, Place place);
}
