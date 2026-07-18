package dysystem.com.greedfinance.infra.repository.impl;

import dysystem.com.greedfinance.domain.model.Role;
import dysystem.com.greedfinance.domain.repository.RoleRepository;
import dysystem.com.greedfinance.infra.entity.RoleEntity;
import dysystem.com.greedfinance.infra.mapper.RoleMapper;
import dysystem.com.greedfinance.infra.repository.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository{

    private final RoleMapper mapper;
    private final RoleRepositoryJpa repositoryJpa;

    @Override
    public Optional<Role> findByName(String name) {
        return repositoryJpa.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Role save(Role role) {
        RoleEntity entity = mapper.toEntity(role);
        return mapper.toDomain(entity);
    }
}
