package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Card;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.infra.entity.AccountEntity;
import dysystem.com.greedfinance.infra.entity.CardEntity;
import dysystem.com.greedfinance.infra.entity.CreditTransactionEntity;
import dysystem.com.greedfinance.infra.entity.TenantEntity;
import dysystem.com.greedfinance.infra.repository.jpa.AccountRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.CreditTransactionsRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.TenantRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class CardMapper {

    private final TenantRepositoryJpa tenantRepositoryJpa;
    private final AccountRepositoryJpa accountRepositoryJpa;
    private final CreditTransactionsRepositoryJpa creditTransactionsRepositoryJpa;

    public Card toDomain(CardEntity entity) {
        if (entity == null) return null;

        return Card.builder()
                .id(entity.getId())
                .name(entity.getName())
                .limit(entity.getLimit() != null ? entity.getLimit() : BigDecimal.ZERO)
                .balance(entity.getBalance() != null ? entity.getBalance() : BigDecimal.ZERO)
                .createAt(entity.getCreateAt())
                .updateAt(entity.getUpdateAt())
                .active(entity.isActive())
                .tenantId(entity.getTenant() != null ? entity.getTenant().getId() : null)
                .accountId(entity.getAccount() != null ? entity.getAccount().getId() : null)
                .creditTransactionsId(
                        entity.getCreditTransactions()
                                .stream()
                                .map(CreditTransactionEntity::getId)
                                .toList()
                )
                .build();
    }

    public CardEntity toEntity(Card domain) {
        if (domain == null) return null;

        CardEntity entity = new CardEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setLimit(domain.getLimit() != null ? domain.getLimit() : BigDecimal.ZERO);
        entity.setBalance(domain.getBalance() != null ? domain.getBalance() : BigDecimal.ZERO);
        entity.setCreateAt(domain.getCreateAt() != null ? domain.getCreateAt() : LocalDateTime.now());
        entity.setUpdateAt(LocalDateTime.now());
        entity.setActive(domain.isActive());

        if (domain.getTenantId() != null) {
            TenantEntity tenant = tenantRepositoryJpa.findById(domain.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant not found: " + domain.getTenantId()));
            entity.setTenant(tenant);
        }

        if (domain.getAccountId() != null) {
            AccountEntity account = accountRepositoryJpa.findById(domain.getAccountId())
                    .orElseThrow(() -> new NotFoundException("Account not found: " + domain.getAccountId()));
            entity.setAccount(account);
        }

        if (domain.getCreditTransactionsId() != null) {
            List<CreditTransactionEntity> creditTransactions = creditTransactionsRepositoryJpa.findAllById(domain.getCreditTransactionsId())
                    .stream()
                    .toList();
            entity.setCreditTransactions(creditTransactions);
        }else{
            entity.setCreditTransactions(Collections.emptyList());
        }
        return entity;
    }
}