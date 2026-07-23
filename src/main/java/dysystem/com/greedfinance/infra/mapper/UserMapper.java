package dysystem.com.greedfinance.infra.mapper;

import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.infra.entity.RoleEntity;
import dysystem.com.greedfinance.infra.entity.UserEntity;
import dysystem.com.greedfinance.infra.repository.jpa.RoleRepositoryJpa;
import dysystem.com.greedfinance.infra.repository.jpa.TenantRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {

    private final TenantRepositoryJpa tenantRepositoryJpa;
    private final RoleRepositoryJpa roleRepositoryJpa;

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        User.UserBuilder builder = User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .createAt(entity.getCreateAt())
                .active(entity.isActive());

        if (entity.getTenant() != null)
            builder.tenantId(entity.getTenant().getId());

        if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
            builder.roleIds(
                    entity.getRoles().stream()
                            .map(RoleEntity::getId)
                            .collect(Collectors.toSet())
            );
        }

        return builder.build();
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setActive(domain.isActive());
        entity.setCreateAt(domain.getCreateAt());

        if (domain.getTenantId() != null)
            entity.setTenant(tenantRepositoryJpa.findById(domain.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant not found")));

        if (domain.getRoleIds() != null && !domain.getRoleIds().isEmpty())
            entity.setRoles(roleRepositoryJpa.findAllById(domain.getRoleIds()));

        return entity;
    }
}