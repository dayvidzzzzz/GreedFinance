package dysystem.com.greedfinance.service.useCase.saving;

import dysystem.com.greedfinance.domain.repository.SavingRepository;
import dysystem.com.greedfinance.dto.response.SavingResponseDTO;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindSavingByIdUseCase {

    private final SavingRepository savingRepository;
    private final ToResponseUtil toResponseUtil;

    public SavingResponseDTO execute(Long id){
        return savingRepository.findById(id)
                .map(toResponseUtil::toSavingResponse)
                .orElseThrow(() -> new NotFoundException("Saving not found"));
    }
}
