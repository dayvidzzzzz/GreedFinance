package dysystem.com.greedfinance.dto.response;

import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record  CreditTransactionResponseDTO(
        Long id,
        BigDecimal amount,
        TransactionStatus status,
        TransactionType type,
        LocalDateTime createAt
){
}
