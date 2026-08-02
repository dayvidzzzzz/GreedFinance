package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.infra.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TenantMapper {

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

        if (entity.getSavings() != null && !entity.getSavings().isEmpty()) {
            builder.savingsId(
                    entity.getSavings().stream()
                            .map(SavingEntity::getId)
                            .collect(Collectors.toList())
            );
        } else {
            builder.savingsId(Collections.emptyList());
        }

        if (entity.getCards() != null && !entity.getCards().isEmpty()) {
            builder.cardsId(
                    entity.getCards().stream()
                            .map(CardEntity::getId)
                            .collect(Collectors.toList())
            );
        } else {
            builder.cardsId(Collections.emptyList());
        }

        return builder.build();
    }

    public TenantEntity toEntity(Tenant domain) {
        if (domain == null) return null;

        TenantEntity entity = new TenantEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setAccounts(Collections.emptyList());
        entity.setTransactions(Collections.emptyList());
        entity.setCategories(Collections.emptyList());
        entity.setSavings(Collections.emptyList());
        entity.setCards(Collections.emptyList());
        return entity;
    }
}