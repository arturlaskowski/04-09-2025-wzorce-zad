package wzorce.oop;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

record TransactionDto(@NotNull @Min(0) BigDecimal amount) {
}
