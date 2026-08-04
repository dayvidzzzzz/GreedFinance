package dysystem.com.greedfinance.service.useCase.creditTransaction;

import dysystem.com.greedfinance.domain.model.Account;
import dysystem.com.greedfinance.domain.model.Card;
import dysystem.com.greedfinance.domain.model.CreditTransactions;
import dysystem.com.greedfinance.domain.repository.AccountRepository;
import dysystem.com.greedfinance.domain.repository.CardRepository;
import dysystem.com.greedfinance.domain.repository.CreditTransactionRepository;
import dysystem.com.greedfinance.domain.repository.TransactionRepository;
import dysystem.com.greedfinance.enums.TransactionType;
import dysystem.com.greedfinance.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class DeleteCreditTransactionUseCase {

    private final CreditTransactionRepository creditTransactionRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void execute(Long id){
        CreditTransactions creditTransactions = creditTransactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Credit card transaction not found"));

        Card card = cardRepository.findById(creditTransactions.getCardId())
                .orElseThrow(() -> new NotFoundException("Card not found"));

        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (transactionRepository.findById(creditTransactions.getTransactionId()).isPresent())
            transactionRepository.deleteById(creditTransactions.getTransactionId());

        BigDecimal newLimit;
        BigDecimal newBalance;

        if (creditTransactions.getTransactionType().equals(TransactionType.INCOME)) {
            newLimit = card.getLimit().subtract(creditTransactions.getAmount());
        } else {
            newLimit = card.getLimit().add(creditTransactions.getAmount());
            newBalance = account.getBalance().add(creditTransactions.getAmount());
            account.setBalance(newBalance);
            accountRepository.save(account);
        }

        card.setLimit(newLimit);
        cardRepository.save(card);

        creditTransactionRepository.deleteById(id);
    }


}
