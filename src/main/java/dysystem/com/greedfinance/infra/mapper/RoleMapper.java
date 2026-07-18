package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Role;
import dysystem.com.greedfinance.infra.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        if (domain == null)
            return null;

        RoleEntity entity = new RoleEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());

        return entity;
    }

    public List<Role> roleDomainList(Collection<RoleEntity> entityList) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public List<RoleEntity> roleEntityList(Collection<Role> domainList) {
        if (domainList == null) return null;
        return domainList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}