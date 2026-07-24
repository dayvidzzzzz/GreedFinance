package dysystem.com.greedfinance.infra.repository.jpa;

import dysystem.com.greedfinance.enums.AccountType;
import dysystem.com.greedfinance.infra.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepositoryJpa extends JpaRepository<AccountEntity, String> {
    List<AccountEntity> findAllByType(AccountType type);

    @Query("SELECT COALESCE(SUM(a.balance), 0) FROM AccountEntity a WHERE a.tenant.id = :tenantId AND a.active = true")
    BigDecimal getTotalBalanceByTenant(@Param("tenantId") String tenantId);

    @Query("SELECT COALESCE(SUM(a.balance), 0) FROM AccountEntity a JOIN a.holders h WHERE h.id = :userId AND a.active = true")
    BigDecimal getTotalBalanceByUser(@Param("userId") String userId);

    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}
