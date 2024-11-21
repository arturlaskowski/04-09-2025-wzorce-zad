package wzorce.oop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
class PaymentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private BigDecimal balance;
    private CardStatus status;

    public PaymentCard(String cardNumber, BigDecimal initialBalance) {
        this.cardNumber = cardNumber;
        this.balance = initialBalance;
        this.status = CardStatus.ACTIVE;
    }
}
