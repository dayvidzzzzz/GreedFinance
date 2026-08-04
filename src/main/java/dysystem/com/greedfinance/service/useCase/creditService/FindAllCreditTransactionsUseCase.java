package dysystem.com.greedfinance.service.useCase.creditService;

import dysystem.com.greedfinance.domain.repository.CreditTransactionRepository;
import dysystem.com.greedfinance.dto.response.CreditTransactionResponseDTO;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAllCreditTransactionsUseCase {

    private final CreditTransactionRepository creditTransactionRepository;
    private final ToResponseUtil toResponseUtil;

    public List<CreditTransactionResponseDTO> execute(){
        return creditTransactionRepository.findAll().stream()
                .map(toResponseUtil::toCreditTransaction)
                .toList();
    }
}
