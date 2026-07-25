package dysystem.com.greedfinance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatus {
    PENDING("Pendente", "A transação foi criada e aguarda processamento."),
    COMPLETED("Concluída", "O valor foi processado e liquidado com sucesso."),
    FAILED("Falhada", "Ocorreu um erro operacional ou sistêmico no processamento."),
    CANCELED("Cancelada", "A transação foi abortada pelo usuário ou pelo sistema.");

    private final String displayName;
    private final String details;
}