package dysystem.com.greedfinance.infra.repository.jpa;

import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;
import dysystem.com.greedfinance.infra.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepositoryJpa extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByTransactionType(TransactionType transactionType);
    List<TransactionEntity> findAllByTransactionStatus(TransactionStatus transactionStatus);
}
