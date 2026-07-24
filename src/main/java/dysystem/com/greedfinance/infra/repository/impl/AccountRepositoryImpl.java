package dysystem.com.greedfinance.infra.repository.impl;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.enums.AccountType;
import dysystem.com.greedfinance.infra.entity.AccountEntity;
import dysystem.com.greedfinance.infra.mapper.AccountMapper;
import dysystem.com.greedfinance.infra.repository.jpa.AccountRepositoryJpa;
import dysystem.com.greedfinance.utils.TenantContext;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountRepositoryJpa accountRepositoryJpa;
    private final AccountMapper mapper;
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
    public Optional<Account> findById(String id) {
        enableTenantFilter();
        return accountRepositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Account save(Account account) {
        enableTenantFilter();
        AccountEntity entity = accountRepositoryJpa.save(mapper.toEntity(account));
        return mapper.toDomain(entity);
    }
    @Override
    public List<Account> findAll() {
        enableTenantFilter();
        return accountRepositoryJpa.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllByAccountType(AccountType type) {
        enableTenantFilter();
        return accountRepositoryJpa.findAllByType(type)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        enableTenantFilter();
        accountRepositoryJpa.deleteById(id);
    }

    @Override
    public BigDecimal getTotalBalanceByTenant(String tenantId) {
        return accountRepositoryJpa.getTotalBalanceByTenant(tenantId);
    }

    @Override
    public BigDecimal getTotalBalanceByUser(String userId) {
        return accountRepositoryJpa.getTotalBalanceByUser(userId);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountRepositoryJpa.findByAccountNumber(accountNumber)
                .map(mapper::toDomain);
    }
}
