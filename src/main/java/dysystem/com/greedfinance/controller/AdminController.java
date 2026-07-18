package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.TenantCreateRequestDTO;
import dysystem.com.greedfinance.dto.response.TenantResponseCreateDTO;
import dysystem.com.greedfinance.service.useCase.tenant.CreateTenantUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/admin")
@AllArgsConstructor
public class AdminController {

    private final CreateTenantUseCase createTenantUseCase;

    @PostMapping
    public ResponseEntity<TenantResponseCreateDTO> create(@RequestBody @Valid TenantCreateRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(createTenantUseCase.execute(dto));
    }
}
