package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Saving;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.infra.entity.SavingEntity;
import dysystem.com.greedfinance.infra.repository.jpa.AccountRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.TenantRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
@AllArgsConstructor
public class SavingMapper {

    private final TenantRepositoryJpa tenantRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;
    private final AccountRepositoryJpa accountRepositoryJpa;

    public Saving toDomain(SavingEntity entity) {
        if (entity == null) return null;

        Saving.SavingBuilder builder = Saving.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .currentAmount(entity.getCurrentAmount())
                .targetAmount(entity.getTargetAmount())
                .createdAt(entity.getCreatedAt())
                .targetDate(entity.getTargetDate())
                .updatedAt(entity.getUpdatedAt())
                .concludedAt(entity.getConcludedAt())
                .status(entity.getStatus())
                .contributionType(entity.getContributionType())
                .allowEarlyWithdrawal(entity.isAllowEarlyWithdrawal());

        if (entity.getTenant() != null)
            builder.tenantId(entity.getTenant().getId());

        if (entity.getUser() != null)
            builder.userId(entity.getUser().getId());

        if (entity.getAccount() != null)
            builder.accountId(entity.getAccount().getId());

        if (entity.getTransactionIds() != null && !entity.getTransactionIds().isEmpty()) {
            builder.transactionIds(
                    new ArrayList<>(entity.getTransactionIds())
            );
        } else {
            builder.transactionIds(Collections.emptyList());
        }

        return builder.build();
    }

    public SavingEntity toEntity(Saving domain) {
        if (domain == null) return null;

        SavingEntity entity = new SavingEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setCurrentAmount(domain.getCurrentAmount());
        entity.setTargetAmount(domain.getTargetAmount());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setTargetDate(domain.getTargetDate());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setConcludedAt(domain.getConcludedAt());
        entity.setStatus(domain.getStatus());
        entity.setContributionType(domain.getContributionType());
        entity.setAllowEarlyWithdrawal(domain.isAllowEarlyWithdrawal());

        if (domain.getTenantId() != null)
            entity.setTenant(tenantRepositoryJpa.findById(domain.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant not found with id: " + domain.getTenantId())));

        if (domain.getUserId() != null)
            entity.setUser(userRepositoryJpa.findById(domain.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found with id: " + domain.getUserId())));

        if (domain.getAccountId() != null)
            entity.setAccount(accountRepositoryJpa.findById(domain.getAccountId())
                    .orElseThrow(() -> new NotFoundException("Account not found with id: " + domain.getAccountId())));

        if (domain.getTransactionIds() != null && !domain.getTransactionIds().isEmpty())
            entity.setTransactionIds(domain.getTransactionIds());
        else
            entity.setTransactionIds(Collections.emptyList());

        return entity;
    }
}