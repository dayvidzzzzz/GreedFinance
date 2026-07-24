package dysystem.com.greedfinance.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dysystem.com.greedfinance.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDTO (
        String id,
        String name,
        String description,
        BigDecimal balance,
        AccountType type,
        String accountNumber,
        boolean active,
        boolean defaultAccount,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updatedAt
) {
}