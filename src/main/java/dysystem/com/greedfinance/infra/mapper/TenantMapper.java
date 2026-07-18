package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.infra.entity.TenantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TenantMapper {

    @Mapping(target = "users", ignore = true)
    Tenant toDomain(TenantEntity entity);

    @Mapping(target = "users", ignore = true)
    TenantEntity toEntity(Tenant domain);
}