package dysystem.com.greedfinance.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TenantCreateRequestDTO(
        @NotBlank
        String name,
        UserMasterRequestDTO userMaster
) {
    public record UserMasterRequestDTO(
            @NotBlank
            String username,
            @Email
            String email,
            @NotBlank
            String password,
            @NotBlank
            String passwordConfirmation
    ){
    }
}
