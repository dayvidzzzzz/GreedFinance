package dysystem.com.greedfinance.infra.repository.jpa;

import dysystem.com.greedfinance.infra.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.tenant " +
            "LEFT JOIN FETCH u.roleEntities " +
            "WHERE u.email = :login OR u.username = :login")
    Optional<UserEntity> findByUsernameOrEmail( String login);
}
