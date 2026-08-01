package dysystem.com.greedfinance.service.useCase.saving;

import dysystem.com.greedfinance.domain.model.*;
import dysystem.com.greedfinance.domain.repository.*;
import dysystem.com.greedfinance.dto.request.SavingAmountRequestDTO;
import dysystem.com.greedfinance.dto.response.SavingResponseDTO;
import dysystem.com.greedfinance.enums.ContributionType;
import dysystem.com.greedfinance.enums.SavingStatus;
import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.SecurityUtils;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddAmountToSavingUseCase {

    private final SavingRepository savingRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TenantRepository tenantRepository;
    private final ToResponseUtil toResponseUtil;
    private final TransactionRepository transactionRepository;

    @Transactional
    public SavingResponseDTO execute(Long id, SavingAmountRequestDTO dto) {
        if (dto.amount() == null || dto.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("Amount must be greater than zero");

        Saving saving = savingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Saving not found with id: " + id));

        String userId = SecurityUtils.getCurrentUserId();
        String tenantId = SecurityUtils.getCurrentTenantId();

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Account account = accountRepository.findById(saving.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        validateSavingStatus(saving);
        validateSavingOwnership(saving, userId, tenantId);
        validateContributionType(saving);
        validateTargetAmount(saving, dto.amount());
        validateSufficientBalance(account, dto.amount());

        Transaction transaction = Transaction.builder()
                .amount(dto.amount())
                .transactionStatus(TransactionStatus.COMPLETED)
                .transactionType(TransactionType.INVESTMENT)
                .createdAt(LocalDateTime.now())
                .accountId(account.getId())
                .tenantId(tenant.getId())
                .categoryId(null)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        account.setBalance(account.getBalance().subtract(dto.amount()));
        accountRepository.save(account);

        List<Long> updatedTransactions = new ArrayList<>(saving.getTransactionIds());
        updatedTransactions.add(savedTransaction.getId());
        saving.setTransactionIds(updatedTransactions);

        BigDecimal newCurrentAmount = saving.getCurrentAmount().add(dto.amount());
        saving.setCurrentAmount(newCurrentAmount);
        saving.setUpdatedAt(LocalDateTime.now());

        if (newCurrentAmount.compareTo(saving.getTargetAmount()) >= 0) {
            saving.setStatus(SavingStatus.COMPLETED);
            saving.setConcludedAt(LocalDateTime.now());
        }

        Saving updatedSaving = savingRepository.save(saving);
        return toResponseUtil.toSavingResponse(updatedSaving);
    }

    private void validateSavingStatus(Saving saving) {
        if (saving.getStatus() == SavingStatus.COMPLETED)
            throw new BusinessException("This saving has already been concluded");

        if (saving.getStatus() == SavingStatus.CANCELLED)
            throw new BusinessException("This saving has been cancelled");

        if (saving.getTargetDate() != null && saving.getTargetDate().isBefore(LocalDateTime.now()))
            throw new BusinessException("Target date has already passed");
    }

    private void validateSavingOwnership(Saving saving, String userId, String tenantId) {
        if (!saving.getTenantId().equals(tenantId))
            throw new BusinessException("Saving does not belong to this tenant");

        if (!saving.getUserId().equals(userId))
            throw new BusinessException("Saving does not belong to this user");
    }

    private void validateContributionType(Saving saving) {
        if (saving.getContributionType() == ContributionType.UNIQUE) {
            boolean hasContributions = saving.getTransactionIds() != null &&
                    !saving.getTransactionIds().isEmpty();

            if (hasContributions)
                throw new BusinessException("This saving only allows one contribution and it has already been made");

        }
    }

    private void validateTargetAmount(Saving saving, BigDecimal amount) {
        BigDecimal newAmount = saving.getCurrentAmount().add(amount);
        if (newAmount.compareTo(saving.getTargetAmount()) > 0)
            throw new BusinessException(String.format("Amount exceeds target. Target: %s, Current: %s, Attempted: %s",
                            saving.getTargetAmount(), saving.getCurrentAmount(), amount));
    }

    private void validateSufficientBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0)
            throw new BusinessException(String.format("Insufficient balance. Available: %s, Required: %s",
                    account.getBalance(), amount));
    }
}