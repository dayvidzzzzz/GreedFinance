package dysystem.com.greedfinance.domain.model;

import dysystem.com.greedfinance.enums.AccountType;
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
public class Account {

    private String id;
    private String name;
    private String description;
    private BigDecimal balance;
    private String agencyNumber;
    private AccountType type;
    private String accountNumber;
    private boolean defaultAccount;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String tenantId;
    private List<String> holderIds;
}
