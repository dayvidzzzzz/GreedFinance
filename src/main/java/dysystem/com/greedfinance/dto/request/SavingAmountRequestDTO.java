package dysystem.com.greedfinance.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SavingAmountRequestDTO(
        @NotNull
        BigDecimal amount
) {
}
