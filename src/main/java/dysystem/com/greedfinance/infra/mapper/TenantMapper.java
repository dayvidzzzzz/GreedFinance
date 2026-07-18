package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.infra.entity.TenantEntity;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper {

    public Tenant toDomain(TenantEntity entity) {
        if (entity == null) return null;

        return Tenant.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public TenantEntity toEntity(Tenant domain) {
        if (domain == null) return null;
        
        TenantEntity entity = new TenantEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        return entity;
    }
}