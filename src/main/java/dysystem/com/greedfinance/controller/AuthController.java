package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.LoginRequestDTO;
import dysystem.com.greedfinance.dto.response.TokenResponseDTO;
import dysystem.com.greedfinance.service.useCase.auth.LoginUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api")
@AllArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;

    @PostMapping("/login/{tenant_id}")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto, @PathVariable String tenant_id){
        return ResponseEntity.ok(loginUseCase.execute(dto, tenant_id));
    }
}
