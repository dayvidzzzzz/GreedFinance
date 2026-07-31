package dysystem.com.greedfinance.infra.repository.impl;

import dysystem.com.greedfinance.domain.model.Saving;
import dysystem.com.greedfinance.domain.repository.SavingRepository;
import dysystem.com.greedfinance.infra.entity.SavingEntity;
import dysystem.com.greedfinance.infra.mapper.SavingMapper;
import dysystem.com.greedfinance.infra.repository.jpa.SavingRepositoryJpa;
import dysystem.com.greedfinance.utils.TenantContext;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SavingRepositoryImpl implements SavingRepository {

    private final SavingMapper mapper;
    private final SavingRepositoryJpa repositoryJpa;
    private final EntityManager entityManager;

    private void enableTenantFilter() {
        String tenantId = TenantContext.getCurrentTenantId();

        if (tenantId != null) {
            Session session = entityManager.unwrap(Session.class);
            Filter filter = session.enableFilter("tenantFilter");
            filter.setParameter("tenantId", tenantId);
        }
    }

    @Override
    public Saving save(Saving data) {
        enableTenantFilter();
        SavingEntity entity = mapper.toEntity(data);
        return mapper.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public Optional<Saving> findById(Long id) {
        enableTenantFilter();
        return repositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Saving> findAll() {
        enableTenantFilter();
        return repositoryJpa.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Long id) {
        enableTenantFilter();
        repositoryJpa.deleteById(id);
    }
}
