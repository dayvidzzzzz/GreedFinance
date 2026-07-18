package dysystem.com.greedfinance.domain.repository;

import dysystem.com.greedfinance.domain.model.Tenant;

import java.util.List;
import java.util.Optional;

public interface TenantRepository {
    Optional<Tenant> findByName(String name);
    boolean existsByName(String name);
    List<Tenant> findAll();
    Optional<Tenant> findById(String id);
    Tenant save(Tenant dto);
}
