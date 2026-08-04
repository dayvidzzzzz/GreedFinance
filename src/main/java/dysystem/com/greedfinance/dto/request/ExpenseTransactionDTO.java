package dysystem.com.greedfinance.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExpenseTransactionDTO(
        @NotNull
        BigDecimal amount,

        @NotBlank
        String idCard

){
}
