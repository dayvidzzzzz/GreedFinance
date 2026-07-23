package dysystem.com.greedfinance.infra.repository.impl;

import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.infra.entity.TenantEntity;
import dysystem.com.greedfinance.infra.mapper.TenantMapper;
import dysystem.com.greedfinance.infra.repository.jpa.TenantRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class TenantRepositoryImpl implements TenantRepository {

    private final TenantMapper mapper;
    private final TenantRepositoryJpa repositoryJpa;

    @Override
    public Optional<Tenant> findByName(String name) {
        return repositoryJpa.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return repositoryJpa.existsByName(name);
    }

    @Override
    public List<Tenant> findAll() {
        return repositoryJpa.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Tenant> findById(String id) {
        return repositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Tenant save(Tenant dto) {
        TenantEntity entity = mapper.toEntity(dto);
        return mapper.toDomain(repositoryJpa.save(entity));
    }
}
