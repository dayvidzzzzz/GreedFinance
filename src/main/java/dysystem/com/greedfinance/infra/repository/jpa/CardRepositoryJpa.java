package dysystem.com.greedfinance.infra.repository.jpa;

import dysystem.com.greedfinance.infra.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepositoryJpa extends JpaRepository<CardEntity, String> {
}
