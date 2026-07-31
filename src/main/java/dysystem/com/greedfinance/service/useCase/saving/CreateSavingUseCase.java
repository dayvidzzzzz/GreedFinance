package dysystem.com.greedfinance.service.useCase.saving;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.model.Saving;
import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.domain.repository.SavingRepository;
import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.request.CreateSavingRequestDTO;
import dysystem.com.greedfinance.dto.response.SavingResponseDTO;
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

@Service
@AllArgsConstructor
public class CreateSavingUseCase {

    private final SavingRepository savingRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TenantRepository tenantRepository;
    private final ToResponseUtil toResponseUtil;

    @Transactional
    public SavingResponseDTO execute(CreateSavingRequestDTO command) {
        String userId = SecurityUtils.getCurrentUserId();
        String tenantId = SecurityUtils.getCurrentTenantId();

        validateCommand(command, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        Account account = null;
        if (command.accountId() != null)
            account = accountRepository.findById(command.accountId())
                    .orElseThrow(() -> new NotFoundException("Account not found with id: " + command.accountId()));

        Tenant tenant = null;
        if (tenantId != null)
            tenant = tenantRepository.findById(tenantId)
                    .orElseThrow(() -> new NotFoundException("Tenant not found with id: " + tenantId));

        Saving saving = Saving.builder()
                .name(command.name())
                .description(command.description())
                .targetAmount(command.targetAmount())
                .currentAmount(BigDecimal.ZERO)
                .targetDate(command.targetDate())
                .tenantId(tenant.getId())
                .userId(user.getId())
                .accountId(account.getId())
                .transactionIds(new ArrayList<>())
                .build();

        return toResponseUtil.toSavingResponse(savingRepository.save(saving));
    }

    private void validateCommand(CreateSavingRequestDTO command, String userId) {
        if (command.name() == null || command.name().trim().isEmpty())
            throw new BusinessException("Saving name is required");

        if (command.targetAmount() == null || command.targetAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("Target amount must be greater than zero");

        if (command.targetDate() == null)
            throw new BusinessException("Target date is required");

        if (command.targetDate().isBefore(LocalDateTime.now()))
            throw new BusinessException("Target date must be in the future");

        if (userId == null || userId.trim().isEmpty())
            throw new BusinessException("User ID is required");
    }
}