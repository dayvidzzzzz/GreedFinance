package dysystem.com.greedfinance.dto.response;

import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        BigDecimal amount,
        TransactionStatus transactionStatus,
        TransactionType transactionType,
        LocalDateTime date
) {
}
