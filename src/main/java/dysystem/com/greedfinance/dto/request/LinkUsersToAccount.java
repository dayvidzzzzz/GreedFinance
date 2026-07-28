package dysystem.com.greedfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record LinkUsersToAccount(
        @NotEmpty
        List<String> usersId
) {
}
