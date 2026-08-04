package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.CreditTransactions;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.infra.entity.CardEntity;
import dysystem.com.greedfinance.infra.entity.CreditTransactionEntity;
import dysystem.com.greedfinance.infra.repository.jpa.CardRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.CategoryRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.TenantRepositoryJpa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CreditTransactionMapper {

    private final CardRepositoryJpa cardRepositoryJpa;
    private final TenantRepositoryJpa tenantRepositoryJpa;
    private final CategoryRepositoryJpa categoryRepositoryJpa;

    public CreditTransactions toDomain(CreditTransactionEntity entity) {
        if (entity == null) return null;

        CreditTransactions transactions = new CreditTransactions();

        transactions.setId(entity.getId());
        transactions.setAmount(entity.getAmount());
        transactions.setCreatedAt(entity.getCreatedAt());
        transactions.setTransactionStatus(entity.getTransactionStatus());
        transactions.setTransactionType(entity.getTransactionType());

        if (entity.getTenant() != null)
            transactions.setTenantId(entity.getTenant().getId());

        if (entity.getCategory() != null)
            transactions.setCategoryId(entity.getCategory().getId());

        if (entity.getCard() != null)
            transactions.setCardId(entity.getCard().getId());

        return transactions;
    }


    public CreditTransactionEntity toEntity(CreditTransactions domain) {
        if (domain == null) return null;

        CreditTransactionEntity transaction = new CreditTransactionEntity();

        transaction.setId(domain.getId());
        transaction.setAmount(domain.getAmount());
        transaction.setTransactionStatus(domain.getTransactionStatus());
        transaction.setTransactionType(domain.getTransactionType());
        transaction.setCreatedAt(domain.getCreatedAt());

        if (domain.getCategoryId() != null)
            transaction.setCategory(categoryRepositoryJpa.findById(domain.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found")));

        if (domain.getTenantId() != null)
            transaction.setTenant(tenantRepositoryJpa.findById(domain.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant not found")));

        if (domain.getCardId() != null) {
            CardEntity card = cardRepositoryJpa.findById(domain.getCardId())
                    .orElseThrow(() -> new NotFoundException("Card not found: " + domain.getCardId()));
            transaction.setCard(card);
        }

        return transaction;
    }
}