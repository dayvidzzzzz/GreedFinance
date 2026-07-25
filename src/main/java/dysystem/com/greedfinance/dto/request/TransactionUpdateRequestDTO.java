package dysystem.com.greedfinance.dto.request;

import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionUpdateRequestDTO(
        @NotNull
        BigDecimal amount,
        TransactionStatus transactionStatus,
        TransactionType transactionType,
        @NotBlank
        String accountId,
        @NotNull
        Long categoryId
){
}
