package dysystem.com.greedfinance.service.useCase.account;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.dto.response.AccountResponseDTO;
import dysystem.com.greedfinance.enums.AccountType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountSelectionList {

    private final AccountRepository accountRepository;

    public List<AccountResponseDTO> findAll(){
        return accountRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<AccountResponseDTO> findAllByType(AccountType accountType){
        return accountRepository.findAllByAccountType(accountType)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
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
