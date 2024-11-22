package wzorce.adapter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class Payment {

    @Id
    private UUID id;
    private String accountNumber;
    private String transactionId;
    private BigDecimal amount;
    private Currency currency;
    private PaymentSystem paymentSystem;

    public Payment(String accountNumber, String transactionId, BigDecimal amount,
                   Currency currency, PaymentSystem paymentSystem) {
        this.id = UUID.randomUUID();
        this.accountNumber = accountNumber;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.paymentSystem = paymentSystem;
    }
}
