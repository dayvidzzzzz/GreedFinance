package dysystem.com.greedfinance.dto.request;

import dysystem.com.greedfinance.enums.ContributionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateSavingRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotNull
        BigDecimal targetAmount,

        LocalDateTime targetDate,

        ContributionType contributionType,

        @NotEmpty
        String accountId,

        boolean allowEarlyWithdrawal
) {
}
