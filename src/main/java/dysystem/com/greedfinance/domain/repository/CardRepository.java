package dysystem.com.greedfinance.domain.repository;

import dysystem.com.greedfinance.domain.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository {
    Card save(Card card);
    Optional<Card> findById(String id);
    void delete(String id);
    List<Card> findAll();
    Optional<Card> findByName(String name);

}
