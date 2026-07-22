package dysystem.com.greedfinance.infra.repository.jpa;

import dysystem.com.greedfinance.infra.entity.RoleEntity;
import dysystem.com.greedfinance.infra.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TenantRepositoryJpa extends JpaRepository<TenantEntity, String> {
    Optional<TenantEntity> findByName(String name);
    boolean existsByName(String name);
}
