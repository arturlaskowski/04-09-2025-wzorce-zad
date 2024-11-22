package wzorce.adapter;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

record NewSystemPaymentDto(
        @NotNull String accountNumber,
        @NotNull String transactionId,
        @NotNull BigDecimal amount,
        @NotNull Currency currency) {
}