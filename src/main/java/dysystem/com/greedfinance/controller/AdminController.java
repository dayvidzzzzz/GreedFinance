package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.TenantCreateRequestDTO;
import dysystem.com.greedfinance.dto.response.TenantResponseCreateDTO;
import dysystem.com.greedfinance.dto.response.TenantResponseDTO;
import dysystem.com.greedfinance.service.useCase.tenant.CreateTenantUseCase;
import dysystem.com.greedfinance.service.useCase.tenant.DeleteTenantUseCase;
import dysystem.com.greedfinance.service.useCase.tenant.FindAllTenant;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final CreateTenantUseCase createTenantUseCase;
    private final FindAllTenant findAllTenant;
    private final DeleteTenantUseCase deleteTenantUseCase;

    @PostMapping
    public ResponseEntity<TenantResponseCreateDTO> create(@RequestBody @Valid TenantCreateRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(createTenantUseCase.execute(dto));
    }

    @GetMapping
    public ResponseEntity<List<TenantResponseDTO>> findAll(){
        return ResponseEntity.ok(findAllTenant.execute());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(String id){
        deleteTenantUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
