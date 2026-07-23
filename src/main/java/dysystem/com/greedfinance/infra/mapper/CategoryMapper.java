package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Category;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.infra.entity.CategoryEntity;
import dysystem.com.greedfinance.infra.repository.jpa.TenantRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper {

    private final TenantRepositoryJpa tenantRepositoryJpa;

    public Category toDomain(CategoryEntity entity){
        if (entity == null) return null;
        Category.CategoryBuilder builder = Category.builder()
                .id(entity.getId())
                .name(entity.getName());

        if (entity.getTenant() != null)
            builder.tenantId(entity.getTenant().getId());

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


        return category;
    }
}
