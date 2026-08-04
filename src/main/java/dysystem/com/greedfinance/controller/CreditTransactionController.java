package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.CreateTransactionRequestDTO;
import dysystem.com.greedfinance.dto.request.ExpenseTransactionDTO;
import dysystem.com.greedfinance.dto.response.CreditExpenseResponseDTO;
import dysystem.com.greedfinance.dto.response.CreditTransactionResponseDTO;
import dysystem.com.greedfinance.service.useCase.creditTransaction.CreateExpenseCreditUseCase;
import dysystem.com.greedfinance.service.useCase.creditTransaction.CreateIncomeCreditUseCase;
import dysystem.com.greedfinance.service.useCase.creditTransaction.DeleteCreditTransactionUseCase;
import dysystem.com.greedfinance.service.useCase.creditTransaction.FindAllCreditTransactionsUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/credit-transactions")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('MASTER', 'USER')")
public class CreditTransactionController {

    private final CreateExpenseCreditUseCase createExpenseCreditUseCase;
    private final CreateIncomeCreditUseCase createIncomeCreditUseCase;
    private final DeleteCreditTransactionUseCase deleteCreditTransactionUseCase;
    private final FindAllCreditTransactionsUseCase findAllCreditTransactionsUseCase;

    @PostMapping("/expense")
    public ResponseEntity<CreditExpenseResponseDTO> createExpense(@RequestBody @Valid ExpenseTransactionDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createExpenseCreditUseCase.execute(dto));
    }

    @PostMapping("/income")
    public ResponseEntity<CreditTransactionResponseDTO> createIncome(@RequestBody @Valid CreateTransactionRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createIncomeCreditUseCase.execute(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        deleteCreditTransactionUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CreditTransactionResponseDTO>> findAll() {
        return ResponseEntity.ok(findAllCreditTransactionsUseCase.execute());
    }
}