package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.CardRequestDTO;
import dysystem.com.greedfinance.dto.request.UpdateCardRequestDTO;
import dysystem.com.greedfinance.dto.response.CardResponseDTO;
import dysystem.com.greedfinance.service.useCase.card.CreateCardUseCase;
import dysystem.com.greedfinance.service.useCase.card.DeleteCardUseCase;
import dysystem.com.greedfinance.service.useCase.card.FindAllCardUseCase;
import dysystem.com.greedfinance.service.useCase.card.UpdateCardUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/cards")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'MASTER')")
public class CardController {

    private final CreateCardUseCase createCardUseCase;
    private final DeleteCardUseCase deleteCardUseCase;
    private final FindAllCardUseCase findAllCardUseCase;
    private final UpdateCardUseCase updateCardUseCase;

    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@Valid @RequestBody CardRequestDTO request) {
        CardResponseDTO response = createCardUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> findAllCards() {
        List<CardResponseDTO> cards = findAllCardUseCase.execute();
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDTO> updateCard(@PathVariable String id,
                                                      @Valid @RequestBody UpdateCardRequestDTO request) {
        CardResponseDTO response = updateCardUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable String id) {
        deleteCardUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}