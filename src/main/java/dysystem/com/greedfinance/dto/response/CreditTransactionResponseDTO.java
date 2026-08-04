package dysystem.com.greedfinance.dto.response;

import dysystem.com.greedfinance.enums.TransactionStatus;

import java.math.BigDecimal;

public record  CreditTransactionResponseDTO(
        Long id,
        BigDecimal amount,
        TransactionStatus status
){
}
