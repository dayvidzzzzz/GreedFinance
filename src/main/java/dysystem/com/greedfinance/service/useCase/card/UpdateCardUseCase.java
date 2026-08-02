package dysystem.com.greedfinance.service.useCase.card;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.model.Card;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.domain.repository.CardRepository;
import dysystem.com.greedfinance.dto.request.UpdateCardRequestDTO;
import dysystem.com.greedfinance.dto.response.CardResponseDTO;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UpdateCardUseCase {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final ToResponseUtil toResponseUtil;

    public CardResponseDTO execute(String id, UpdateCardRequestDTO dto){
        if (cardRepository.findByName(dto.name()).isPresent())
            throw new BusinessException("There´s already a card with this name");

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Card not found"));

        Account account = accountRepository.findById(dto.accountId())
                        .orElseThrow(() -> new NotFoundException("Account not found"));

        card.setAccountId(account.getId());
        card.setName(dto.name());
        card.setLimit(dto.limit());
        card.setActive(dto.ative());
        card.setUpdateAt(LocalDateTime.now());

        return toResponseUtil.toCardResponse(card);
    }
}
