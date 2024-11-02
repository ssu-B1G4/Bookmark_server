package B1G4.bookmark.security.principal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.AuthException;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member =
                memberRepository
                        .findById(Long.parseLong(userId))
                        .orElseThrow(() -> new AuthException(ErrorStatus.USER_NOT_FOUND));

        return new PrincipalDetails(member);
    }
}