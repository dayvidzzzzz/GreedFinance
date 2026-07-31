package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.CreateSavingRequestDTO;
import dysystem.com.greedfinance.dto.response.SavingResponseDTO;
import dysystem.com.greedfinance.service.useCase.saving.CreateSavingUseCase;
import dysystem.com.greedfinance.service.useCase.saving.DeleteSavingUseCase;
import dysystem.com.greedfinance.service.useCase.saving.FindAllSavingUseCase;
import dysystem.com.greedfinance.service.useCase.saving.FindSavingByIdUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/savings")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'MASTER')")
public class SavingController {

    private final CreateSavingUseCase createSavingUseCase;
    private final FindAllSavingUseCase findAllSavingUseCase;
    private final FindSavingByIdUseCase findSavingByIdUseCase;
    private final DeleteSavingUseCase deleteSavingUseCase;

    @PostMapping
    public ResponseEntity<SavingResponseDTO> createSaving(@Valid @RequestBody CreateSavingRequestDTO request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                    .body(createSavingUseCase.execute(request));
    }

    @GetMapping
    public ResponseEntity<List<SavingResponseDTO>> findAllSavings() {
        return ResponseEntity.ok(findAllSavingUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingResponseDTO> findSavingById(@PathVariable Long id) {
        return ResponseEntity.ok(findSavingByIdUseCase.execute(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaving(@PathVariable Long id) {
        deleteSavingUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}