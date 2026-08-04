package dysystem.com.greedfinance.utils;

import dysystem.com.greedfinance.domain.model.*;
import dysystem.com.greedfinance.dto.response.*;
import org.springframework.stereotype.Component;

@Component
public class ToResponseUtil {

    public TenantResponseDTO toTenantResponse(Tenant tenant){
        return new TenantResponseDTO(
                tenant.getId(),
                tenant.getName()
        );
    }

    public UserResponseDTO toUserResponse(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.isActive(),
                user.getCreateAt()
        );
    }

    public SavingResponseDTO toSavingResponse(Saving saving){
        return new SavingResponseDTO(
                saving.getId(),
                saving.getName(),
                saving.getDescription(),
                saving.getCurrentAmount(),
                saving.getTargetAmount(),
                saving.getCreatedAt(),
                saving.getTargetDate(),
                saving.getUpdatedAt(),
                saving.getConcludedAt(),
                saving.getStatus(),
                saving.getContributionType(),
                saving.isAllowEarlyWithdrawal()
        );
    }

    public CardResponseDTO toCardResponse(Card card){
        return new CardResponseDTO(
                card.getId(),
                card.getName(),
                card.getLimit(),
                card.isActive()
        );

    }

    public CreditTransactionResponseDTO toCreditTransaction(CreditTransactions creditTransactions){
        return new CreditTransactionResponseDTO(
                creditTransactions.getId(),
                creditTransactions.getAmount(),
                creditTransactions.getTransactionStatus()
        );
    }
}
