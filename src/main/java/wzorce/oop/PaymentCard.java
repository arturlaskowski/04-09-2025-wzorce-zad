package wzorce.oop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    public void activate() {
        if (this.status == CardStatus.ACTIVE) {
            throw new CardOperationException("Card is already active");
        }
        this.status = CardStatus.ACTIVE;
    }

    public void block() {
        if (this.status != CardStatus.ACTIVE) {
            throw new CardOperationException("Card cannot be blocked unless it is active");
        }
        this.status = CardStatus.BLOCKED;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }
        if (this.status != CardStatus.ACTIVE) {
            throw new CardOperationException("Deposits can only be made to an active card");
        }
        this.balance = this.balance.add(amount).setScale(2, RoundingMode.HALF_EVEN);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        }
        if (this.status != CardStatus.ACTIVE) {
            throw new CardOperationException("Withdrawals can only be made from an active card");
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new CardOperationException("Insufficient funds for withdrawal");
        }
        this.balance = this.balance.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
    }
}
