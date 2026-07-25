package dysystem.com.greedfinance.domain.repository;

import dysystem.com.greedfinance.domain.model.Transaction;
import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(Long id);
    List<Transaction> findAll();
    List<Transaction> findAllByTransactionType(TransactionType transactionType);
    List<Transaction> findAllByTransactionStatus(TransactionStatus transactionStatus);
    void deleteById(Long id);
}
