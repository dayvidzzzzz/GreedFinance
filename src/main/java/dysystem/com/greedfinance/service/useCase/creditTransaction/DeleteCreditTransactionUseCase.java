package dysystem.com.greedfinance.service.useCase.creditTransaction;

import dysystem.com.greedfinance.domain.repository.CreditTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteCreditTransactionUseCase {

    private final CreditTransactionRepository creditTransactionRepository;

    @Transactional
    public void execute(Long id){
        if (creditTransactionRepository.findById(id).isPresent())
            creditTransactionRepository.deleteById(id);
    }
}
