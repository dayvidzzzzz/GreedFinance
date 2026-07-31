package dysystem.com.greedfinance.domain.model;

import dysystem.com.greedfinance.enums.ContributionType;
import dysystem.com.greedfinance.enums.SavingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Saving {

    private Long id;
    private String name;
    private String description;

    private BigDecimal currentAmount;
    private BigDecimal targetAmount;

    private LocalDateTime createdAt;
    private LocalDateTime targetDate;
    private LocalDateTime updatedAt;
    private LocalDateTime concludedAt;

    private SavingStatus status;
    private ContributionType contributionType;

    private String tenantId;
    private String userId;
    private String accountId;
    private List<Long> transactionIds;

    private boolean allowEarlyWithdrawal;
}