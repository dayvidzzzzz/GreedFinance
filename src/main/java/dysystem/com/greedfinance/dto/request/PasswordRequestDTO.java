package dysystem.com.greedfinance.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordRequestDTO(
        @NotBlank
        String password,

        @NotBlank
        String passwordConfirmation
) {
}
