package dysystem.com.greedfinance.service.useCase.transaction;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.model.Category;
import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.domain.model.Transaction;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.domain.repository.CategoryRepository;
import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.domain.repository.TransactionRepository;
import dysystem.com.greedfinance.dto.request.CreateTransactionRequestDTO;
import dysystem.com.greedfinance.dto.response.TransactionResponseDTO;
import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CreateExpenseTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final TenantRepository tenantRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransactionResponseDTO execute(CreateTransactionRequestDTO dto) {
        String tenantId = SecurityUtils.getCurrentTenantId();

        if (dto.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("Amount must be greater than zero");

        Account account = accountRepository.findById(dto.accountId())
                .orElseThrow(() -> new NotFoundException("Account not found: " + dto.accountId()));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + dto.categoryId()));

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant not found: " + tenantId));

        if (account.getBalance().compareTo(dto.amount()) < 0)
            throw new BusinessException("Insufficient balance. Available: " + account.getBalance());

        BigDecimal newBalance = account.getBalance().subtract(dto.amount());
        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .amount(dto.amount())
                .transactionType(TransactionType.EXPENSE)
                .transactionStatus(TransactionStatus.COMPLETED)
                .accountId(dto.accountId())
                .categoryId(dto.categoryId())
                .tenantId(tenant.getId())
                .build();

        Transaction newTransaction = transactionRepository.save(transaction);
        return toResponse(newTransaction);
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