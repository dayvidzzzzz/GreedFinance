package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Transaction;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.infra.entity.TransactionEntity;
import dysystem.com.greedfinance.infra.repository.jpa.AccountRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.CategoryRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.TenantRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionMapper {

    private final CategoryRepositoryJpa categoryRepositoryJpa;
    private final TenantRepositoryJpa tenantRepositoryJpa;
    private final AccountRepositoryJpa accountRepositoryJpa;

    public Transaction toDomain(TransactionEntity entity){
        if (entity == null) return null;

        Transaction.TransactionBuilder builder = Transaction.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .transactionType(entity.getTransactionType())
                .transactionStatus(entity.getTransactionStatus())
                .createdAt(entity.getCreatedAt());

        if (entity.getAccount() != null)
                builder.accountId(entity.getAccount().getId());

        if (entity.getTenant() != null)
                builder.tenantId(entity.getTenant().getId());

        if (entity.getCategory() != null)
                builder.categoryId(entity.getCategory().getId());

        return builder.build();
    }

    public TransactionEntity toEntity(Transaction domain){
        if (domain == null) return null;

        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(domain.getId());
        transaction.setAmount(domain.getAmount());
        transaction.setTransactionType(domain.getTransactionType());
        transaction.setTransactionStatus(domain.getTransactionStatus());
        transaction.setCreatedAt(domain.getCreatedAt());

        if (domain.getCategoryId() != null)
            transaction.setCategory(categoryRepositoryJpa.findById(domain.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found")));

        if (domain.getAccountId() != null)
            transaction.setAccount(accountRepositoryJpa.findById(domain.getAccountId())
                    .orElseThrow(() -> new NotFoundException("Account not found")));

        if (domain.getTenantId() != null)
            transaction.setTenant(tenantRepositoryJpa.findById(domain.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant not found")));

        return transaction;
    }
}
