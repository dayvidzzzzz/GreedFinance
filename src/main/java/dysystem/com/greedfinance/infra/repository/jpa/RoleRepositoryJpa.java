package dysystem.com.greedfinance.infra.repository.jpa;

import dysystem.com.greedfinance.infra.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepositoryJpa extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
