package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.infra.entity.AccountEntity;
import dysystem.com.greedfinance.infra.entity.UserEntity;
import dysystem.com.greedfinance.infra.repository.jpa.TenantRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class AccountMapper {

    private final TenantRepositoryJpa tenantRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;

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
                .isActive(entity.isActive())
                .defaultAccount(entity.isDefaultAccount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .tenantId(entity.getTenant() != null ? entity.getTenant().getId() : null);

        if (entity.getHolders() != null && !entity.getHolders().isEmpty()) {
            builder.holderIds(entity.getHolders()
                    .stream()
                    .map(UserEntity::getId)
                    .collect(Collectors.toList())
            );
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
                    .orElseThrow(() -> new NotFoundException("Tenant not found")));

        if (domain.getHolderIds() != null && !domain.getHolderIds().isEmpty()) {
            ArrayList<UserEntity> holders = new ArrayList<>(
                    userRepositoryJpa.findAllById(domain.getHolderIds()));
            entity.setHolders(holders);
        } else {
            entity.setHolders(new ArrayList<>());
        }

        return entity;
    }
}