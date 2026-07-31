package dysystem.com.greedfinance.service.useCase.saving;

import dysystem.com.greedfinance.domain.repository.SavingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteSavingUseCase {
    private final SavingRepository savingRepository;

    public void execute(Long id){
        if (savingRepository.findById(id).isPresent())
            savingRepository.delete(id);
    }
}
