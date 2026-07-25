package dysystem.com.greedfinance.service.useCase.transaction;

import dysystem.com.greedfinance.domain.model.Transaction;
import dysystem.com.greedfinance.domain.repository.TransactionRepository;
import dysystem.com.greedfinance.dto.response.TransactionResponseDTO;
import dysystem.com.greedfinance.enums.TransactionStatus;
import dysystem.com.greedfinance.enums.TransactionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SelectionTransactionList {

    private final TransactionRepository transactionRepository;

    public List<TransactionResponseDTO> findAll(){
        return transactionRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> findAllByStatus(TransactionStatus status){
        return transactionRepository.findAllByTransactionStatus(status).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> findAllByType(TransactionType type){
        return transactionRepository.findAllByTransactionType(type).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponseDTO toResponse(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTransactionStatus(),
                transaction.getTransactionType(),
                transaction.getCreatedAt()
        );
    }
}
