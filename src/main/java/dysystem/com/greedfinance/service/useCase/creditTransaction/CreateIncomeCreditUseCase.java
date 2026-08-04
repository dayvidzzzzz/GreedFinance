package dysystem.com.greedfinance.service.useCase.creditTransaction;

import dysystem.com.greedfinance.domain.model.*;
import dysystem.com.greedfinance.domain.repository.CardRepository;
import dysystem.com.greedfinance.domain.repository.CategoryRepository;
import dysystem.com.greedfinance.domain.repository.CreditTransactionRepository;
import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.dto.request.CreateTransactionRequestDTO;
import dysystem.com.greedfinance.dto.response.CreditTransactionResponseDTO;
import dysystem.com.greedfinance.enums.TransactionType;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.SecurityUtils;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CreateIncomeCreditUseCase {

    private final CreditTransactionRepository creditTransactionRepository;
    private final CategoryRepository categoryRepository;
    private final TenantRepository tenantRepository;
    private final CardRepository cardRepository;
    private final ToResponseUtil toResponseUtil;

    @Transactional
    public CreditTransactionResponseDTO execute(CreateTransactionRequestDTO dto) {
        String tenantId = SecurityUtils.getCurrentTenantId();

        if (dto.amount() == null || dto.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("Amount must be greater than zero");

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + dto.categoryId()));

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant not found: " + tenantId));

        Card card = cardRepository.findById(dto.cardId())
                .orElseThrow(() -> new NotFoundException("Card not found: " + dto.cardId()));

        if (card.getLimit().compareTo(dto.amount()) < 0)
            throw new BusinessException("Insufficient card limit. Available: " + card.getLimit() +
                    ", Requested: " + dto.amount());

        BigDecimal newCardBalance = card.getBalance().add(dto.amount());
        card.setBalance(newCardBalance);
        cardRepository.save(card);

        CreditTransactions creditTransactions = CreditTransactions.builder()
                .amount(dto.amount())
                .transactionType(TransactionType.INCOME)
                .cardId(card.getId())
                .categoryId(category.getId())
                .tenantId(tenant.getId())
                .build();

        CreditTransactions savedTransaction = creditTransactionRepository.save(creditTransactions);
        return toResponseUtil.toCreditTransaction(savedTransaction);
    }
}