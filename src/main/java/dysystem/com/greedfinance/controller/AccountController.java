package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.AccountRequestDTO;
import dysystem.com.greedfinance.dto.response.AccountResponseDTO;
import dysystem.com.greedfinance.enums.AccountType;
import dysystem.com.greedfinance.service.useCase.account.AccountSelectionList;
import dysystem.com.greedfinance.service.useCase.account.CreateAccountUseCase;
import dysystem.com.greedfinance.service.useCase.account.DeleteAccountUseCase;
import dysystem.com.greedfinance.service.useCase.account.FindAccountByIdUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/accounts")
@AllArgsConstructor
@PreAuthorize("hasRole('USER')")
public class AccountController {

    private final AccountSelectionList accountSelectionList;
    private final CreateAccountUseCase createAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;
    private final FindAccountByIdUseCase findAccountById;


    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@RequestBody @Valid AccountRequestDTO dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                    .body(createAccountUseCase.execute(dto));

    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getById(@PathVariable String id){
        return ResponseEntity.ok(findAccountById.execute(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        deleteAccountUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAll(){
        return ResponseEntity.ok(accountSelectionList.findAll());
    }

    @GetMapping("/filters/account-type/{type}")
    public ResponseEntity<List<AccountResponseDTO>> getAllByType(AccountType type){
        return ResponseEntity.ok(accountSelectionList.findAllByType(type));
    }
}
