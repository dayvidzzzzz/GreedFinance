package dysystem.com.greedfinance.service.useCase.account;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.model.User;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.domain.repository.UserRepository;
import dysystem.com.greedfinance.dto.request.LinkUsersToAccount;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LinkUsersToAccountUseCase {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public void execute(String accountId, LinkUsersToAccount dto){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        List<User> users = userRepository.findAllById(dto.usersId());
        account.setHolderIds(dto.usersId());

        accountRepository.save(account);
    }
}
