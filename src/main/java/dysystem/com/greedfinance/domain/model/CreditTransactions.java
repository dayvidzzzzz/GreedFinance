package dysystem.com.greedfinance.domain.model;

import dysystem.com.greedfinance.enums.TransactionStatus;

import dysystem.com.greedfinance.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditTransactions{

    private Long id;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private LocalDateTime createdAt;
    private String tenantId;
    private Long categoryId;
    private String cardId;
    private Long transactionId;
}
