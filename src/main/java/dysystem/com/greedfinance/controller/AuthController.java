package dysystem.com.greedfinance.controller;

import dysystem.com.greedfinance.dto.request.LoginRequestDTO;
import dysystem.com.greedfinance.dto.request.PasswordRequestDTO;
import dysystem.com.greedfinance.dto.response.TokenResponseDTO;
import dysystem.com.greedfinance.service.useCase.auth.LoginUseCase;
import dysystem.com.greedfinance.service.useCase.user.SetUserPasswordUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@AllArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final SetUserPasswordUseCase setUserPasswordUseCase;

    @PostMapping("/login/{tenant_id}")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto,
                                                  @PathVariable String tenant_id){

        return ResponseEntity.ok(loginUseCase.execute(dto, tenant_id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/login/set-password")
    public ResponseEntity<?> setUserPassword(@RequestBody @Valid PasswordRequestDTO dto){
        setUserPasswordUseCase.execute(dto);
        return ResponseEntity.ok().build();
    }
}
