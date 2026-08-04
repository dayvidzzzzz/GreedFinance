package dysystem.com.greedfinance.domain.model;

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
public class Card {

    private String id;
    private String name;
    private BigDecimal limit;
    private BigDecimal balance;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private boolean active;
    private String accountId;
    private String tenantId;
    private List<Long> creditTransactionsId;
}
