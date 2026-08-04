package dysystem.com.greedfinance.service.useCase.creditTransaction;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.model.Card;
import dysystem.com.greedfinance.domain.model.Category;
import dysystem.com.greedfinance.domain.model.CreditTransactions;
import dysystem.com.greedfinance.domain.model.Transaction;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.domain.repository.CardRepository;
import dysystem.com.greedfinance.domain.repository.CategoryRepository;
import dysystem.com.greedfinance.domain.repository.CreditTransactionRepository;
import dysystem.com.greedfinance.domain.repository.TransactionRepository;
import dysystem.com.greedfinance.dto.request.ExpenseTransactionDTO;
import dysystem.com.greedfinance.dto.response.CreditExpenseResponseDTO;
import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateExpenseCreditUseCase {

    private final CreditTransactionRepository creditTransactionRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public CreditExpenseResponseDTO execute(ExpenseTransactionDTO dto) {
        Card card = cardRepository.findById(dto.idCard())
                .orElseThrow(() -> new NotFoundException("Card not found"));

        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        validateCardLimit(card.getLimit(), card.getBalance(), dto.amount());
        validateAccountBalance(account.getBalance(), dto.amount());
        String tenantId = SecurityUtils.getCurrentTenantId();

        Category category = categoryRepository.findByName("Credito")
                .orElseGet(() -> categoryRepository.save(
                        Category.builder()
                                .name("Credito")
                                .tenantId(tenantId)
                                .build()
                ));

        TransactionStatus status = account.getBalance().compareTo(dto.amount()) == 0
                ? TransactionStatus.COMPLETED
                : TransactionStatus.PENDING;

        Transaction transaction = Transaction.builder()
                .amount(dto.amount())
                .transactionStatus(status)
                .transactionType(TransactionType.EXPENSE)
                .accountId(account.getId())
                .categoryId(category.getId())
                .tenantId(tenantId)
                .build();
        transaction = transactionRepository.save(transaction);

        CreditTransactions creditTransactions = CreditTransactions.builder()
                .amount(dto.amount())
                .cardId(card.getId())
                .categoryId(category.getId())
                .tenantId(tenantId)
                .transactionStatus(status)
                .transactionType(TransactionType.EXPENSE)
                .transactionId(transaction.getId())
                .build();

        BigDecimal newCardBalance = card.getBalance().subtract(dto.amount());
        card.setBalance(newCardBalance);
        cardRepository.save(card);

        BigDecimal newAccountBalance = account.getBalance().subtract(dto.amount());
        account.setBalance(newAccountBalance);
        accountRepository.save(account);

        CreditTransactions savedTransaction = creditTransactionRepository.save(creditTransactions);

        return toCreditTransaction(savedTransaction, card.getName());
    }

    private void validateCardLimit(BigDecimal limit, BigDecimal currentBalance, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("Amount must be greater than zero");

        BigDecimal newBalance = currentBalance.add(amount);
        if (newBalance.compareTo(limit) > 0)
            throw new BusinessException("This purchase would exceed your card limit.");
    }

    private void validateAccountBalance(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) < 0)
            throw new BusinessException("You don't have enough money in your account");
    }

    private CreditExpenseResponseDTO toCreditTransaction(CreditTransactions creditTransactions, String cardName){
        return new CreditExpenseResponseDTO(
                creditTransactions.getId(),
                creditTransactions.getAmount(),
                creditTransactions.getTransactionStatus(),
                creditTransactions.getCreatedAt(),
                cardName
        );
    }
}