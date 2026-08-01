package dysystem.com.greedfinance.service.useCase.saving;

import dysystem.com.greedfinance.domain.repository.SavingRepository;
import dysystem.com.greedfinance.dto.response.SavingResponseDTO;
import dysystem.com.greedfinance.enums.SavingStatus;
import dysystem.com.greedfinance.utils.ToResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindSavingByStatusUseCase {

    private final SavingRepository savingRepository;
    private final ToResponseUtil toResponseUtil;

    public List<SavingResponseDTO> execute(SavingStatus status){
        return savingRepository.findAllByStatus(status).stream()
                .map(toResponseUtil::toSavingResponse)
                .toList();
    }
}
