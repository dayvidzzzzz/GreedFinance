package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.infra.entity.AccountEntity;
import dysystem.com.greedfinance.infra.entity.CardEntity;
import dysystem.com.greedfinance.infra.entity.TransactionEntity;
import dysystem.com.greedfinance.infra.entity.UserEntity;
import dysystem.com.greedfinance.infra.repository.jpa.CardRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.TenantRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.TransactionRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AccountMapper {

    private final TenantRepositoryJpa tenantRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;
    private final TransactionRepositoryJpa transactionRepositoryJpa;
    private final CardRepositoryJpa cardRepositoryJpa;

    public Account toDomain(AccountEntity entity) {
        if (entity == null) return null;

        Account.AccountBuilder builder = Account.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .balance(entity.getBalance())
                .type(entity.getType())
                .accountNumber(entity.getAccountNumber())
                .agencyNumber(entity.getAgencyNumber())
                .defaultAccount(entity.isDefaultAccount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .tenantId(entity.getTenant() != null ? entity.getTenant().getId() : null)
                .transactionsId(entity.getTransactions() != null ?
                        entity.getTransactions().stream()
                                .map(TransactionEntity::getId)
                                .toList() : new ArrayList<>())
                .cardsId(entity.getCards() != null ?
                        entity.getCards().stream()
                                .map(CardEntity::getId)
                                .toList() : new ArrayList<>());

        if (entity.getHolders() != null && !entity.getHolders().isEmpty()) {
            builder.holderIds(entity.getHolders()
                    .stream()
                    .map(UserEntity::getId)
                    .collect(Collectors.toList())
            );
        } else {
            builder.holderIds(new ArrayList<>());
        }
        return builder.build();
    }

    public AccountEntity toEntity(Account domain) {
        if (domain == null) return null;

        AccountEntity entity = new AccountEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setBalance(domain.getBalance() != null ? domain.getBalance() : BigDecimal.ZERO);
        entity.setType(domain.getType());
        entity.setAccountNumber(domain.getAccountNumber());
        entity.setAgencyNumber(domain.getAgencyNumber());
        entity.setActive(domain.isActive());
        entity.setDefaultAccount(domain.isDefaultAccount());

        if (domain.getTenantId() != null)
            entity.setTenant(tenantRepositoryJpa.findById(domain.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant not found: " + domain.getTenantId())));

        List<String> holderIds = domain.getHolderIds();
        if (holderIds != null && !holderIds.isEmpty())
            entity.setHolders(userRepositoryJpa.findAllById(holderIds));
        else
            entity.setHolders(new ArrayList<>());

        List<Long> transactionIds = domain.getTransactionsId();
        if (transactionIds != null && !transactionIds.isEmpty())
            entity.setTransactions(transactionRepositoryJpa.findAllById(transactionIds));
        else
            entity.setTransactions(new ArrayList<>());

        List<String> cardIds = domain.getCardsId();
        if (cardIds != null && !cardIds.isEmpty())
            entity.setCards(cardRepositoryJpa.findAllById(cardIds));
        else
            entity.setCards(new ArrayList<>());

        return entity;
    }
}