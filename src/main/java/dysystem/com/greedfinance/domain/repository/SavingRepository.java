package dysystem.com.greedfinance.domain.repository;

import dysystem.com.greedfinance.domain.model.Saving;

import java.util.List;
import java.util.Optional;

public interface SavingRepository {
    Saving save(Saving data);
    Optional<Saving> findById(Long id);
    List<Saving> findAll();
    void delete(Long id);
}