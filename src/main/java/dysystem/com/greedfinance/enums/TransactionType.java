package dysystem.com.greedfinance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    INCOME("IN", "Entrada / Receita"),
    EXPENSE("OUT", "Saída / Despesa"),
    INVESTMENT("INV", "Investimento / Guardado");

    private final String code;
    private final String description;
}
