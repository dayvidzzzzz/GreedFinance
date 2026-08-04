package dysystem.com.greedfinance.infra.repository.impl;

import dysystem.com.greedfinance.domain.model.CreditTransactions;
import dysystem.com.greedfinance.domain.repository.CreditTransactionRepository;
import dysystem.com.greedfinance.infra.entity.CreditTransactionEntity;
import dysystem.com.greedfinance.infra.mapper.CreditTransactionMapper;
import dysystem.com.greedfinance.infra.repository.jpa.CreditTransactionsRepositoryJpa;
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
public class CreditTransactionRepositoryImpl implements CreditTransactionRepository {

    private final CreditTransactionsRepositoryJpa repositoryJpa;
    private final CreditTransactionMapper mapper;
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
    public CreditTransactions save(CreditTransactions transaction) {
        enableTenantFilter();
        CreditTransactionEntity entity = mapper.toEntity(transaction);
        return mapper.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public Optional<CreditTransactions> findById(Long id) {
        enableTenantFilter();
        return repositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<CreditTransactions> findAll() {
        enableTenantFilter();
        return repositoryJpa.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        enableTenantFilter();
        repositoryJpa.deleteById(id);
    }
}
