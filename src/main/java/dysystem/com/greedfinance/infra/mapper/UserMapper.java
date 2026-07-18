package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.Role;
import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.infra.entity.RoleEntity;
import dysystem.com.greedfinance.infra.entity.TenantEntity;
import dysystem.com.greedfinance.infra.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {

    private final RoleMapper roleMapper;

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        Tenant tenant = null;
        if (entity.getTenant() != null) {
            tenant = Tenant.builder()
                    .id(entity.getTenant().getId())
                    .name(entity.getTenant().getName())
                    .build();
        }

        List<Role> roles = null;
        if (entity.getRoleEntities() != null && !entity.getRoleEntities().isEmpty()) {
            roles = entity.getRoleEntities().stream()
                    .map(roleMapper::toDomain)
                    .collect(Collectors.toList());
        }

        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .createAt(entity.getCreateAt())
                .isActive(entity.isActive())
                .tenant(tenant)
                .roles(roles)
                .build();
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setCreateAt(domain.getCreateAt());
        entity.setActive(domain.isActive());

        if (domain.getTenant() != null && domain.getTenant().getId() != null) {
            TenantEntity tenantEntity = new TenantEntity();
            tenantEntity.setId(domain.getTenant().getId());
            entity.setTenant(tenantEntity);
        }

        if (domain.getRoles() != null) {
            List<RoleEntity> roleEntities = domain.getRoles().stream()
                    .map(roleMapper::toEntity)
                    .collect(Collectors.toList());
            entity.setRoleEntities(roleEntities);
        }

        return entity;
    }

    public List<User> userDomainList(List<UserEntity> entityList) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public List<UserEntity> userEntityList(List<User> domainList) {
        if (domainList == null) return null;
        return domainList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}