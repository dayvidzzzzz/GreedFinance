package dysystem.com.greedfinance.infra.repository.jpa;

import dysystem.com.greedfinance.infra.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepositoryJpa extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    List<CategoryEntity> findAllByName(String name);
}
