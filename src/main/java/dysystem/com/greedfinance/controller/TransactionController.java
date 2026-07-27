package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.CreateTransactionRequestDTO;
import dysystem.com.greedfinance.dto.request.TransactionUpdateRequestDTO;
import dysystem.com.greedfinance.dto.response.TransactionResponseDTO;
import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;
import dysystem.com.greedfinance.service.useCase.transaction.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/transactions")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'MASTER')")
public class TransactionController {

    private final CreateIncomeTransactionUseCase incomeTransactionUseCase;
    private final CreateExpenseTransactionUseCase expenseTransactionUseCase;
    private final UpdateTransactionUseCase updateTransactionUseCase;
    private final DeleteTransactionById deleteTransactionById;
    private final SelectionTransactionList transactionList;

    @PostMapping("/income")
    public ResponseEntity<TransactionResponseDTO> createIncome(@RequestBody @Valid CreateTransactionRequestDTO data){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                    .body(incomeTransactionUseCase.execute(data));
    }

    @PostMapping("/expense")
    public ResponseEntity<TransactionResponseDTO> createExpense(@RequestBody @Valid CreateTransactionRequestDTO data){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                    .body(expenseTransactionUseCase.execute(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> update(@PathVariable Long id,
                                                         @RequestBody @Valid TransactionUpdateRequestDTO dto){
        return ResponseEntity.ok(updateTransactionUseCase.execute(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        deleteTransactionById.execute(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> findAll(){
        return ResponseEntity.ok(transactionList.findAll());
    }

    @GetMapping("/filters/type/{type}")
    public ResponseEntity<List<TransactionResponseDTO>> findAllByType(@PathVariable TransactionType type){
        return ResponseEntity.ok(transactionList.findAllByType(type));
    }

    @GetMapping("/filters/status/{status}")
    public ResponseEntity<List<TransactionResponseDTO>> findAllByStatus(@PathVariable TransactionStatus status){
        return ResponseEntity.ok(transactionList.findAllByStatus(status));
    }

}
