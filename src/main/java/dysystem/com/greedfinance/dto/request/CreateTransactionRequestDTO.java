package dysystem.com.greedfinance.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransactionRequestDTO(
        @NotNull
        BigDecimal amount,
        @NotBlank
        String accountId,
        @NotNull
        Long categoryId
){
}
