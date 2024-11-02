package B1G4.bookmark.redis.repository;

import org.springframework.data.repository.CrudRepository;

import B1G4.bookmark.redis.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {}
