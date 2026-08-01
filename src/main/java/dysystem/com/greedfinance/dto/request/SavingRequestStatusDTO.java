package dysystem.com.greedfinance.dto.request;

import dysystem.com.greedfinance.enums.SavingStatus;
import jakarta.validation.constraints.NotNull;

public record SavingRequestStatusDTO(
        @NotNull
        SavingStatus status
) {
}
