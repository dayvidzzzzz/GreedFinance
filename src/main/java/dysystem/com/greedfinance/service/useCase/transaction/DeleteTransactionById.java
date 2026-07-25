package dysystem.com.greedfinance.service.useCase.transaction;

import dysystem.com.greedfinance.domain.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteTransactionById {

    private final TransactionRepository transactionRepository;

    public void execute(Long id){
        transactionRepository.deleteById(id);
    }
}
