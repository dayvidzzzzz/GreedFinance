package dysystem.com.greedfinance.service.useCase.card;

import dysystem.com.greedfinance.domain.repository.CardRepository;
import dysystem.com.greedfinance.dto.response.CardResponseDTO;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindAllCardUseCase {

    private final CardRepository cardRepository;
    private final ToResponseUtil toResponseUtil;

    public List<CardResponseDTO> execute(){
        return cardRepository.findAll().stream()
                .map(toResponseUtil::toCardResponse)
                .toList();
    }
}
