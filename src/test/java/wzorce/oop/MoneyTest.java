package wzorce.oop;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void canCreateMoneyFromBigDecimal() {
        assertEquals(new BigDecimal("10.00"), new Money(new BigDecimal("10.00")).amount());
        assertEquals(new BigDecimal("17.89"), new Money(new BigDecimal("17.89")).amount());
        assertEquals(new BigDecimal("0.00"), new Money(new BigDecimal("0.00")).amount());
    }

    @Test
    void shouldSetScale() {
        assertEquals(new BigDecimal("10.01"), new Money(new BigDecimal("10.006")).amount());
        assertEquals(new BigDecimal("10.00"), new Money(new BigDecimal("10.004")).amount());
        assertEquals(new BigDecimal("10.00"), new Money(new BigDecimal("10.005")).amount());
    }

    @Test
    void canAddMoney() {
        assertEquals(new Money(new BigDecimal("10.00")), new Money(new BigDecimal("3.00")).add(new Money(new BigDecimal("7.00"))));
        assertEquals(new Money(new BigDecimal("35.41")), new Money(new BigDecimal("18.94")).add(new Money(new BigDecimal("16.47"))));
        assertEquals(new Money(new BigDecimal("7.00")), new Money(new BigDecimal("0.00")).add(new Money(new BigDecimal("7.00"))));
    }

    @Test
    void canSubtractMoney() {
        assertEquals(new Money(new BigDecimal("30.00")), new Money(new BigDecimal("50.00")).subtract(new Money(new BigDecimal("20.00"))));
        assertEquals(Money.ZERO, new Money(new BigDecimal("50.00")).subtract(new Money(new BigDecimal("50.00"))));
        assertEquals(new Money(new BigDecimal("27.04")), new Money(new BigDecimal("56.36")).subtract(new Money(new BigDecimal("29.32"))));
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new Money(null));
        assertEquals("Amount cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {
        var negativeAmount = new BigDecimal(-1);
        var exception = assertThrows(IllegalArgumentException.class, () -> new Money(negativeAmount));
        assertEquals("Amount cannot be negative", exception.getMessage());
    }

    @Test
    void isGreaterThanOrEqualToShouldReturnTrue() {
        var money1 = new Money(new BigDecimal("50.00"));
        var money2 = new Money(new BigDecimal("50.00"));
        var money3 = new Money(new BigDecimal("30.00"));
        assertTrue(money1.isGreaterThanOrEqualTo(money3));
        assertTrue(money1.isGreaterThanOrEqualTo(money2));
    }

    @Test
    void isGreaterThanOrEqualToShouldReturnFalse() {
        var money1 = new Money(new BigDecimal("30.00"));
        var money2 = new Money(new BigDecimal("50.00"));
        assertFalse(money1.isGreaterThanOrEqualTo(money2));
    }
}