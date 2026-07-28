package dysystem.com.greedfinance.dto.request;

import dysystem.com.greedfinance.enums.AccountType;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record AccountRequestDTO (
        @NotBlank
        String name,
        @NotBlank
        String description,
        BigDecimal balance,
        AccountType type,
        @NotBlank
        String accountNumber,
        @NotBlank
        String agencyNumber
){
}
