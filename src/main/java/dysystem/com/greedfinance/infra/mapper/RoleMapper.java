package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Role;
import dysystem.com.greedfinance.infra.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role toDomain(RoleEntity entity) {
        if (entity == null) return null;

        return Role.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public RoleEntity toEntity(Role domain) {
        if (domain == null) return null;

        RoleEntity entity = new RoleEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        return entity;
    }
}