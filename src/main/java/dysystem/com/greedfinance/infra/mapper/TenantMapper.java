package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.infra.entity.*;
import dysystem.com.greedfinance.infra.repository.jpa.AccountRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.TransactionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TenantMapper {

    private final AccountRepositoryJpa accountRepositoryJpa;
    private final TransactionRepositoryJpa transactionRepositoryJpa;

    public Tenant toDomain(TenantEntity entity) {
        if (entity == null) return null;

        Tenant.TenantBuilder builder = Tenant.builder()
                .id(entity.getId())
                .name(entity.getName());

        if (entity.getUsers() != null && !entity.getUsers().isEmpty()) {
            builder.usersId(
                    entity.getUsers().stream()
                            .map(UserEntity::getId)
                            .collect(Collectors.toList())
            );
        } else {
            builder.usersId(Collections.emptyList());
        }

        if (entity.getCategories() != null && !entity.getCategories().isEmpty()) {
            builder.categoriesId(
                    entity.getCategories().stream()
                            .map(CategoryEntity::getId)
                            .collect(Collectors.toList())
            );
        } else {
            builder.categoriesId(Collections.emptyList());
        }

        if (entity.getAccounts() != null && !entity.getAccounts().isEmpty()) {
            builder.accountsId(
                    entity.getAccounts().stream()
                            .map(AccountEntity::getId)
                            .collect(Collectors.toList())
            );
        } else {
            builder.accountsId(Collections.emptyList());
        }

        if (entity.getTransactions() != null && !entity.getAccounts().isEmpty()) {
            builder.transactionsId(
                    entity.getTransactions().stream()
                            .map(TransactionEntity::getId)
                            .collect(Collectors.toList())
            );
        } else {
            builder.transactionsId(Collections.emptyList());
        }

        return builder.build();
    }

    public TenantEntity toEntity(Tenant domain) {
        if (domain == null) return null;

        TenantEntity entity = new TenantEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setAccounts(accountRepositoryJpa.findAllById(domain.getAccountsId()));
        entity.setTransactions(transactionRepositoryJpa.findAllById(domain.getTransactionsId()));
        return entity;
    }
}