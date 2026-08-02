package dysystem.com.greedfinance.service.useCase.card;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.model.Card;
import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.domain.repository.CardRepository;
import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.dto.request.CardRequestDTO;
import dysystem.com.greedfinance.dto.response.CardResponseDTO;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.SecurityUtils;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateCardUseCase {

    private final CardRepository cardRepository;
    private final TenantRepository tenantRepository;
    private final AccountRepository accountRepository;
    private final ToResponseUtil toResponseUtil;

    @Transactional
    public CardResponseDTO execute(CardRequestDTO dto){
        if (dto.limit().compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("Amount must be greater than zero");

        if (cardRepository.findByName(dto.name()).isPresent())
            throw new BusinessException("There´s already a card with this name");

        Account account = accountRepository.findById(dto.accountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        String idTenant = SecurityUtils.getCurrentTenantId();
        Tenant tenant = tenantRepository.findById(idTenant)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));

        Card card = Card.builder()
                .id(UUID.randomUUID().toString())
                .name(dto.name())
                .limit(dto.limit())
                .accountId(account.getId())
                .tenantId(tenant.getId())
                .build();
        Card savedCard = cardRepository.save(card);

        return toResponseUtil.toCardResponse(savedCard);
    }
}
