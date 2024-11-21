package wzorce.oop;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

record CreatePaymentCardDto(
        @NotBlank String cardNumber,
        @NotNull @Min(0) BigDecimal initialBalance
) {
}
