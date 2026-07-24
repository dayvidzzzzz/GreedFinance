package dysystem.com.greedfinance.domain.repository;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.enums.AccountType;
import dysystem.com.greedfinance.infra.entity.AccountEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(String id);
    Account save(Account account);
    List<Account> findAll();
    List<Account> findAllByAccountType(AccountType type);
    void deleteById(String id);
    BigDecimal getTotalBalanceByTenant(String tenantId);
    BigDecimal getTotalBalanceByUser(String userId);
    Optional<Account> findByAccountNumber(String accountNumber);
}
