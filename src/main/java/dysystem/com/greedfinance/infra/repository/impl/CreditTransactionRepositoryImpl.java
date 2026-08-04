package dysystem.com.greedfinance.infra.repository.impl;

import dysystem.com.greedfinance.domain.model.CreditTransactions;
import dysystem.com.greedfinance.domain.repository.CreditTransactionRepository;
import dysystem.com.greedfinance.infra.entity.CreditTransactionEntity;
import dysystem.com.greedfinance.infra.mapper.CreditTransactionMapper;
import dysystem.com.greedfinance.infra.repository.jpa.CreditTransactionsRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CreditTransactionRepositoryImpl implements CreditTransactionRepository {

    private final CreditTransactionsRepositoryJpa repositoryJpa;
    private final CreditTransactionMapper mapper;

    @Override
    public CreditTransactions save(CreditTransactions transaction) {
        CreditTransactionEntity entity = mapper.toEntity(transaction);
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<CreditTransactions> findById(Long id) {
        return repositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<CreditTransactions> findAll() {
        return repositoryJpa.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repositoryJpa.deleteById(id);
    }
}
