package dysystem.com.greedfinance.service.useCase.account;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.dto.response.AccountResponseDTO;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindAccountByIdUseCase {

    private final AccountRepository accountRepository;

    public AccountResponseDTO execute(String id){
        return accountRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    private AccountResponseDTO toResponse(Account account){
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
