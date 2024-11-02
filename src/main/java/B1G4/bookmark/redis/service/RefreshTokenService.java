package B1G4.bookmark.redis.service;

import B1G4.bookmark.security.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import B1G4.bookmark.apiPayload.code.status.ErrorStatus;
import B1G4.bookmark.apiPayload.exception.AuthException;
import B1G4.bookmark.redis.domain.RefreshToken;
import B1G4.bookmark.redis.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public boolean isEqualsToken(String refreshToken) {
        RefreshToken savedrefreshToken =
                refreshTokenRepository
                        .findById(jwtTokenProvider.getId(refreshToken))
                        .orElseThrow(() -> new AuthException(ErrorStatus.NOT_CONTAIN_TOKEN));

        return savedrefreshToken.getToken().equals(refreshToken);
    }

    @Transactional
    public void saveToken(String refreshToken) {
        RefreshToken newRefreshToken =
                RefreshToken.builder()
                        .id(jwtTokenProvider.getId(refreshToken))
                        .token(refreshToken)
                        .build();
        refreshTokenRepository.save(newRefreshToken);
    }

    @Transactional
    public void deleteToken(Long userId) {
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findById(userId)
                        .orElseThrow(() -> new AuthException(ErrorStatus.NOT_CONTAIN_TOKEN));
        refreshTokenRepository.delete(refreshToken);
    }
}
