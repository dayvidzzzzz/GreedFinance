package dysystem.com.greedfinance.dto.response;

import java.math.BigDecimal;

public record CardResponseDTO(
        String id,
        String name,
        BigDecimal limit,
        BigDecimal balance,
        boolean ative
) {
}
