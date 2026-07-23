package dysystem.com.greedfinance.infra.repository.impl;

import dysystem.com.greedfinance.domain.model.Category;
import dysystem.com.greedfinance.domain.repository.CategoryRepository;
import dysystem.com.greedfinance.infra.entity.CategoryEntity;
import dysystem.com.greedfinance.infra.mapper.CategoryMapper;
import dysystem.com.greedfinance.infra.repository.jpa.CategoryRepositoryJpa;
import dysystem.com.greedfinance.utils.TenantContext;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryRepositoryJpa categoryRepositoryJpa;
    private final CategoryMapper categoryMapper;
    private final EntityManager entityManager;

    private void enableTenantFilter() {
        String tenantId = TenantContext.getCurrentTenantId();

        if (tenantId != null) {
            Session session = entityManager.unwrap(Session.class);
            Filter filter = session.enableFilter("tenantFilter");
            filter.setParameter("tenantId", tenantId);
        }
    }

    @Override
    public Optional<Category> findById(Long id) {
        enableTenantFilter();
        return categoryRepositoryJpa.findById(id)
                .map(categoryMapper::toDomain);
    }

    @Override
    public Optional<Category> findByName(String name) {
        enableTenantFilter();
        return categoryRepositoryJpa.findByName(name)
                .map(categoryMapper::toDomain);
    }

    @Override
    public Category save(Category category) {
        enableTenantFilter();
        CategoryEntity entity = categoryMapper.toEntity(category);
        return categoryMapper.toDomain(categoryRepositoryJpa.save(entity));
    }

    @Override
    public List<Category> findAll() {
        enableTenantFilter();
        return categoryRepositoryJpa.findAll()
                .stream()
                .map(categoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> findAllByName(String name) {
        enableTenantFilter();
        return categoryRepositoryJpa.findAllByName(name)
                .stream()
                .map(categoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        enableTenantFilter();
        categoryRepositoryJpa.deleteById(id);
    }
}
