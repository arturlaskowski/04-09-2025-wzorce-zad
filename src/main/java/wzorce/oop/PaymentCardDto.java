package wzorce.oop;

import java.math.BigDecimal;

record PaymentCardDto(
        Long id,
        String cardNumber,
        BigDecimal balance,
        CardStatus status) {
}
