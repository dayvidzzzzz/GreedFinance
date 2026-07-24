package dysystem.com.greedfinance.service.useCase.account;

import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAccountUseCase {

    private final AccountRepository accountRepository;

    public void execute(String id){
        if (!accountRepository.findById(id).isPresent())
            throw new NotFoundException("This account don´t exist");
        accountRepository.deleteById(id);
    }
}
