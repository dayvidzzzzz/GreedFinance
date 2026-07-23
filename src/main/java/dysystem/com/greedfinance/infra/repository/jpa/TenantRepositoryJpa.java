package dysystem.com.greedfinance.infra.repository.jpa;

import dysystem.com.greedfinance.infra.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepositoryJpa extends JpaRepository<TenantEntity, String> {
    Optional<TenantEntity> findByName(String name);
    boolean existsByName(String name);
}
