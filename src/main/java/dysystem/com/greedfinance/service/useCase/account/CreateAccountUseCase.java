package dysystem.com.greedfinance.service.useCase.account;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.model.Tenant;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.domain.repository.TenantRepository;
import dysystem.com.greedfinance.dto.request.AccountRequestDTO;
import dysystem.com.greedfinance.dto.response.AccountResponseDTO;
import dysystem.com.greedfinance.handler.exception.BadRequestException;
import dysystem.com.greedfinance.handler.exception.BusinessException;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CreateAccountUseCase {

    private final AccountRepository accountRepository;
    private final TenantRepository tenantRepository;

    @Transactional
    public AccountResponseDTO execute(AccountRequestDTO dto) {
        for (int i = 0; i < dto.agencyNumber().length(); i++) {
            char c = dto.agencyNumber().charAt(i);
            if (!Character.isDigit(c))
                throw new BadRequestException("In account number this character is not a digit " + "'" + c + "'");
        }

        if (accountRepository.findByAccountNumber(dto.accountNumber()).isPresent())
            throw new BusinessException("There´s already a account with this account number");

        String tenantId = SecurityUtils.getCurrentTenantId();
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant not found: " + tenantId));

        String userId = SecurityUtils.getCurrentUserId();

        String accountId = UUID.randomUUID().toString();
        Account.AccountBuilder accountBuilder = Account.builder()
                .id(accountId)
                .name(dto.name())
                .description(dto.description())
                .balance(dto.balance() != null ? dto.balance() : java.math.BigDecimal.ZERO)
                .type(dto.type())
                .accountNumber(dto.accountNumber())
                .agencyNumber(dto.agencyNumber())
                .holderIds(List.of(userId))
                .tenantId(tenantId);
        List<String> existingAccounts = tenant.getAccountsId();

        if (existingAccounts == null || existingAccounts.isEmpty())
            accountBuilder.defaultAccount(true);
        else
            accountBuilder.defaultAccount(false);

        Account savedAccount = accountRepository.save(accountBuilder.build());
        return toResponse(savedAccount);
    }

    private AccountResponseDTO toResponse(Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getName(),
                account.getDescription(),
                account.getBalance(),
                account.getType(),
                account.getAgencyNumber(),
                account.isActive(),
                account.isDefaultAccount(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}