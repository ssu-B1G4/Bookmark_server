package B1G4.bookmark.repository;

import B1G4.bookmark.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
