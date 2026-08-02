package dysystem.com.greedfinance.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant {
    private String id;
    private String name;
    private List<String> usersId;
    private List<Long> categoriesId;
    private List<String> accountsId;
    private List<Long> transactionsId;
    private List<Long> savingsId;
    private List<String> cardsId;
}
