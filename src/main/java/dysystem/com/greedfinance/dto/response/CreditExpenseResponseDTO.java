package dysystem.com.greedfinance.dto.response;

import dysystem.com.greedfinance.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreditExpenseResponseDTO(
        Long id,
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime createAt,
        String cardName
) {
}
