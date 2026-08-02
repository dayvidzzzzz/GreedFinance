package dysystem.com.greedfinance.infra.repository.impl;

import dysystem.com.greedfinance.domain.model.Card;
import dysystem.com.greedfinance.domain.repository.CardRepository;
import dysystem.com.greedfinance.infra.entity.CardEntity;
import dysystem.com.greedfinance.infra.mapper.CardMapper;
import dysystem.com.greedfinance.infra.repository.jpa.CardRepositoryJpa;
import dysystem.com.greedfinance.utils.TenantContext;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CardRepositoryImpl implements CardRepository {

    private final CardRepositoryJpa cardRepositoryJpa;
    private final CardMapper mapper;
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
    public Card save(Card card) {
        CardEntity entity = mapper.toEntity(card);
        return mapper.toDomain(cardRepositoryJpa.save(entity));
    }

    @Override
    public Optional<Card> findById(String id) {
        return cardRepositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void delete(String id) {
        cardRepositoryJpa.deleteById(id);
    }

    @Override
    public List<Card> findAll() {
        return cardRepositoryJpa.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Card> findByName(String name) {
        return cardRepositoryJpa.findByName(name)
                .map(mapper::toDomain);
    }
}
