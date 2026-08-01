package dysystem.com.greedfinance.infra.repository.jpa;

import dysystem.com.greedfinance.enums.SavingStatus;
import dysystem.com.greedfinance.infra.entity.SavingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingRepositoryJpa extends JpaRepository<SavingEntity, Long> {
    List<SavingEntity> findAllByStatus(SavingStatus status);
}
