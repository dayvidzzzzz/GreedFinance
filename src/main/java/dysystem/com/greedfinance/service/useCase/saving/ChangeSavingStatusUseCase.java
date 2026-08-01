package dysystem.com.greedfinance.service.useCase.saving;

import dysystem.com.greedfinance.domain.model.Saving;
import dysystem.com.greedfinance.domain.repository.SavingRepository;
import dysystem.com.greedfinance.dto.request.SavingRequestStatusDTO;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ChangeSavingStatusUseCase {

    private final SavingRepository savingRepository;

    @Transactional
    public void execute(Long savingId, SavingRequestStatusDTO dto){
        Saving saving = savingRepository.findById(savingId)
                .orElseThrow(() -> new NotFoundException("Saving not found"));

        saving.setStatus(dto.status());
        saving.setUpdatedAt(LocalDateTime.now());
        savingRepository.save(saving);
    }
}
