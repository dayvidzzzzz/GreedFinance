package dysystem.com.greedfinance.infra.repository.impl;

import dysystem.com.greedfinance.domain.model.Transaction;
import dysystem.com.greedfinance.domain.repository.TransactionRepository;
import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;
import dysystem.com.greedfinance.infra.entity.TransactionEntity;
import dysystem.com.greedfinance.infra.mapper.TransactionMapper;
import dysystem.com.greedfinance.infra.repository.jpa.TransactionRepositoryJpa;
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
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionRepositoryJpa repositoryJpa;
    private final TransactionMapper mapper;
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
    public Transaction save(Transaction transaction) {
        enableTenantFilter();
        TransactionEntity entity = mapper.toEntity(transaction);
        return mapper.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        enableTenantFilter();
        return repositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Transaction> findAll() {
        return repositoryJpa.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Transaction> findAllByTransactionType(TransactionType transactionType) {
        return repositoryJpa.findAllByTransactionType(transactionType).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Transaction> findAllByTransactionStatus(TransactionStatus transactionStatus) {
        return repositoryJpa.findAllByTransactionStatus(transactionStatus).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repositoryJpa.deleteById(id);
    }
}
