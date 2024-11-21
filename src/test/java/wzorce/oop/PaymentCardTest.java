package wzorce.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentCardTest {

    private PaymentCard card;

    @BeforeEach
    void setUp() {
        card = new PaymentCard("1234567890123456", new BigDecimal("100.00"));
    }

    @Test
    void shouldThrowExceptionWhenActivatingAlreadyActiveCard() {
        assertThatThrownBy(() -> card.activate())
                .isInstanceOf(CardOperationException.class)
                .hasMessage("Card is already active");
    }

    @Test
    void shouldBlockActiveCard() {
        card.block();
        assertThat(card.getStatus()).isEqualByComparingTo(CardStatus.BLOCKED);
    }

    @Test
    void shouldThrowExceptionWhenBlockingNonActiveCard() {
        card.block(); // First block to change status
        assertThatThrownBy(() -> card.block())
                .isInstanceOf(CardOperationException.class)
                .hasMessage("Card cannot be blocked unless it is active");
    }

    @Test
    void shouldThrowExceptionForNegativeDepositAmount() {
        var amount = new BigDecimal("-10");
        assertThatThrownBy(() -> card.deposit(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Deposit amount cannot be negative");
    }

    @Test
    void shouldThrowExceptionWhenDepositingToBlockedCard() {
        card.block();
        var amount = new BigDecimal("50");
        assertThatThrownBy(() -> card.deposit(amount))
                .isInstanceOf(CardOperationException.class)
                .hasMessage("Deposits can only be made to an active card");
    }

    @Test
    void shouldIncreaseBalanceWhenDepositingToActiveCard() {
        var amount = new BigDecimal("50");
        card.deposit(amount);
        assertThat(card.getBalance()).isEqualByComparingTo("150.00");
    }

    @Test
    void shouldThrowExceptionForNegativeWithdrawalAmount() {
        var amount = new BigDecimal("-20");
        assertThatThrownBy(() -> card.withdraw(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Withdrawal amount cannot be negative");
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingMoreThanBalance() {
        var amount = new BigDecimal("200");
        assertThatThrownBy(() -> card.withdraw(amount))
                .isInstanceOf(CardOperationException.class)
                .hasMessage("Insufficient funds for withdrawal");
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingFromBlockedCard() {
        card.block();
        var amount = new BigDecimal("20");
        assertThatThrownBy(() -> card.withdraw(amount))
                .isInstanceOf(CardOperationException.class)
                .hasMessage("Withdrawals can only be made from an active card");
    }

    @Test
    void shouldDecreaseBalanceWhenWithdrawingFromActiveCard() {
        var amount = new BigDecimal("20");
        card.withdraw(amount);
        assertThat(card.getBalance()).isEqualByComparingTo("80.00");
    }
}