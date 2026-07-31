package dysystem.com.greedfinance.dto.response;

import dysystem.com.greedfinance.enums.ContributionType;
import dysystem.com.greedfinance.enums.SavingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SavingResponseDTO(
         Long id,
         String name,
         String description,
         BigDecimal currentAmount,
         BigDecimal targetAmount,
         LocalDateTime createdAt,
         LocalDateTime targetDate,
         LocalDateTime updatedAt,
         LocalDateTime concludedAt,
         SavingStatus status,
         ContributionType contributionType,
         boolean allowEarlyWithdrawal
) {
}
