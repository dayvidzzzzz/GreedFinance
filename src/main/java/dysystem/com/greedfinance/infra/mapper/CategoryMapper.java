package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Category;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.infra.entity.CategoryEntity;
import dysystem.com.greedfinance.infra.entity.CreditTransactionEntity;
import dysystem.com.greedfinance.infra.repository.jpa.CreditTransactionsRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.TenantRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class CategoryMapper {

    private final TenantRepositoryJpa tenantRepositoryJpa;
    private final CreditTransactionsRepositoryJpa creditTransactionsRepositoryJpa;

    public Category toDomain(CategoryEntity entity){
        if (entity == null) return null;
        Category.CategoryBuilder builder = Category.builder()
                .id(entity.getId())
                .name(entity.getName());

        if (entity.getTenant() != null)
            builder.tenantId(entity.getTenant().getId());

        if (entity.getCreditTransactions() != null)
            builder.creditTransactionsId(entity.getCreditTransactions().stream()
                    .map(CreditTransactionEntity::getId)
                    .toList());
        else
            builder.creditTransactionsId(Collections.emptyList());

        return builder.build();
    }

    public CategoryEntity toEntity(Category domain){
        if (domain == null) return null;

        CategoryEntity category = new CategoryEntity();
        category.setId(domain.getId());
        category.setName(domain.getName());

        if (domain.getTenantId() != null)
            category.setTenant(tenantRepositoryJpa.findById(domain.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant not found")));

        if (domain.getCreditTransactionsId() != null)
            category.setCreditTransactions(creditTransactionsRepositoryJpa.findAllById(domain.getCreditTransactionsId()));
        else
            category.setCreditTransactions(Collections.emptyList());
        return category;
    }
}
