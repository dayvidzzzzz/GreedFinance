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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class DeleteCreditTransactionUseCase {

    private final CreditTransactionRepository creditTransactionRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void execute(Long id){
        log.info("Deleting credit transaction with id: {}", id);

        // 1. Busca o CreditTransaction
        CreditTransactions creditTransactions = creditTransactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Credit card transaction not found"));

        // 2. Busca o Card
        Card card = cardRepository.findById(creditTransactions.getCardId())
                .orElseThrow(() -> new NotFoundException("Card not found"));

        // 3. Busca a Account
        Account account = accountRepository.findById(card.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        // 4. Atualiza os saldos ANTES de deletar
        BigDecimal newCardBalance;

        if (creditTransactions.getTransactionType().equals(TransactionType.INCOME)) {
            newCardBalance = card.getBalance().subtract(creditTransactions.getAmount());
        } else {
            newCardBalance = card.getBalance().add(creditTransactions.getAmount());
            BigDecimal newAccountBalance = account.getBalance().add(creditTransactions.getAmount());
            account.setBalance(newAccountBalance);
            accountRepository.save(account);
        }

        card.setBalance(newCardBalance);
        cardRepository.save(card);

        // 5. Deleta a Transaction associada (se existir)
        Long transactionId = creditTransactions.getTransactionId();
        if (transactionId != null) {
            transactionRepository.findById(transactionId)
                    .ifPresent(transaction -> {
                        transactionRepository.deleteById(transaction.getId());
                        log.info("Deleted associated transaction with id: {}", transactionId);
                    });
        } else {
            log.warn("Credit transaction {} has no associated transaction", id);
        }

        // 6. Deleta o CreditTransaction
        creditTransactionRepository.deleteById(id);
        log.info("Credit transaction {} deleted successfully", id);
    }
}