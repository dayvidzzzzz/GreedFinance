package dysystem.com.greedfinance.domain.repository;

import dysystem.com.greedfinance.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    Category save(Category category);
    List<Category> findAll();
    List<Category> findAllByName(String name);
    void delete(Long id);
}
