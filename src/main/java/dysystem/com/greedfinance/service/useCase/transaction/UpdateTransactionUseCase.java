package dysystem.com.greedfinance.service.useCase.transaction;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.model.Category;
import dysystem.com.greedfinance.domain.model.Transaction;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.domain.repository.CategoryRepository;
import dysystem.com.greedfinance.domain.repository.TransactionRepository;
import dysystem.com.greedfinance.dto.request.TransactionUpdateRequestDTO;
import dysystem.com.greedfinance.dto.response.TransactionResponseDTO;
import dysystem.com.greedfinance.enums.TransactionType;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class UpdateTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransactionResponseDTO execute(Long idTransaction,TransactionUpdateRequestDTO dto) {

        String tenantId = SecurityUtils.getCurrentTenantId();

        if (dto.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("Amount must be greater than zero");

        Transaction oldTransaction = transactionRepository.findById(idTransaction)
                .orElseThrow(() -> new NotFoundException("Transaction not found: " + idTransaction));

        if (!oldTransaction.getTenantId().equals(tenantId))
            throw new BusinessException("Transaction does not belong to current tenant");

        Account newAccount = accountRepository.findById(dto.accountId())
                .orElseThrow(() -> new NotFoundException("Account not found: " + dto.accountId()));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + dto.categoryId()));

        Account oldAccount = accountRepository.findById(oldTransaction.getAccountId())
                .orElseThrow(() -> new NotFoundException("Original account not found: " + oldTransaction.getAccountId()));

        revertTransactionEffect(oldAccount, oldTransaction);
        accountRepository.save(oldAccount);

        if (dto.transactionType() == TransactionType.EXPENSE &&
                newAccount.getBalance().compareTo(dto.amount()) < 0) {
            applyTransactionEffect(oldAccount, oldTransaction);
            accountRepository.save(oldAccount);
            throw new BusinessException("Insufficient balance. Available: " + newAccount.getBalance() +
                    ", Required: " + dto.amount());
        }

        applyTransactionEffect(newAccount, dto);
        accountRepository.save(newAccount);

        oldTransaction.setAmount(dto.amount());
        oldTransaction.setTransactionType(dto.transactionType());
        oldTransaction.setTransactionStatus(dto.transactionStatus());
        oldTransaction.setAccountId(newAccount.getId());
        oldTransaction.setCategoryId(category.getId());

        Transaction updatedTransaction = transactionRepository.save(oldTransaction);
        return toResponse(updatedTransaction);
    }

    private void revertTransactionEffect(Account account, Transaction transaction) {
        if (transaction.getTransactionType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        } else {
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        }
    }

    private void applyTransactionEffect(Account account, TransactionUpdateRequestDTO dto) {
        if (dto.transactionType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance().add(dto.amount()));
        } else {
            account.setBalance(account.getBalance().subtract(dto.amount()));
        }
    }

    private void applyTransactionEffect(Account account, Transaction transaction) {
        if (transaction.getTransactionType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        } else {
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        }
    }

    private TransactionResponseDTO toResponse(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTransactionStatus(),
                transaction.getTransactionType(),
                transaction.getCreatedAt()
        );
    }
}