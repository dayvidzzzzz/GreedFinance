package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Role;
import dysystem.com.greedfinance.infra.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toDomain(RoleEntity entity);
    RoleEntity toEntity(Role domain);
}