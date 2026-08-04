package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.CreateTransactionRequestDTO;
import dysystem.com.greedfinance.dto.request.ExpenseTransactionDTO;
import dysystem.com.greedfinance.dto.response.CreditTransactionResponseDTO;
import dysystem.com.greedfinance.service.useCase.creditService.CreateExpenseCreditUseCase;
import dysystem.com.greedfinance.service.useCase.creditService.CreateIncomeCreditUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/credit-transactions")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('MASTER', 'USER')")
public class CreditTransactionController {

    private final CreateExpenseCreditUseCase createExpenseCreditUseCase;
    private final CreateIncomeCreditUseCase createIncomeCreditUseCase;

    @PostMapping("/expense")
    public ResponseEntity<CreditTransactionResponseDTO> createExpense(@RequestBody @Valid ExpenseTransactionDTO dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                    .body(createExpenseCreditUseCase.execute(dto));
    }

    @PostMapping("/income")
    public ResponseEntity<CreditTransactionResponseDTO> createIncome(@RequestBody @Valid CreateTransactionRequestDTO dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                    .body(createIncomeCreditUseCase.execute(dto));
    }
}
