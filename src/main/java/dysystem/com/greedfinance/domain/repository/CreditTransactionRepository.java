package dysystem.com.greedfinance.domain.repository;

import dysystem.com.greedfinance.domain.model.CreditTransactions;

import java.util.List;
import java.util.Optional;

public interface CreditTransactionRepository {
    CreditTransactions save(CreditTransactions transaction);
    Optional<CreditTransactions> findById(Long id);
    List<CreditTransactions> findAll();
    void deleteById(Long id);
}
