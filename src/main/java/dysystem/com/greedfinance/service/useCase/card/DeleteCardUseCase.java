package dysystem.com.greedfinance.service.useCase.card;

import dysystem.com.greedfinance.domain.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DeleteCardUseCase {

    private final CardRepository cardRepository;

    public void execute(String id){
        if (cardRepository.findById(id).isPresent())
            cardRepository.delete(id);
    }
}
