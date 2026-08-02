package dysystem.com.greedfinance.dto.request;

import java.math.BigDecimal;

public record UpdateCardRequestDTO(
        String name,
        BigDecimal limit,
        boolean ative,
        String accountId
){
}
