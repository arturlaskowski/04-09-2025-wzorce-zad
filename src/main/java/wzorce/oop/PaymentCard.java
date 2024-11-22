package wzorce.oop;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class PaymentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    @AttributeOverride(name = "amount", column = @Column(name = "balance"))
    private Money balance;
    private CardStatus status;

    public PaymentCard(String cardNumber, Money initialBalance) {
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

    public void deposit(Money amount) {
        if (this.status != CardStatus.ACTIVE) {
            throw new CardOperationException("Deposits can only be made to an active card");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(Money amount) {
        if (this.status != CardStatus.ACTIVE) {
            throw new CardOperationException("Withdrawals can only be made from an active card");
        }
        if (!this.balance.isGreaterThanOrEqualTo(amount)) {
            throw new CardOperationException("Insufficient funds for withdrawal");
        }
        this.balance = this.balance.subtract(amount);
    }
}
