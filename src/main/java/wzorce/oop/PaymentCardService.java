package wzorce.oop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
class PaymentCardService {

    private final PaymentCardRepository paymentCardRepository;

    public Long createPaymentCard(CreatePaymentCardDto dto) {
        var initialBalanceRounded = dto.initialBalance().setScale(2, RoundingMode.HALF_EVEN);
        var paymentCard = new PaymentCard(dto.cardNumber(), initialBalanceRounded);
        return paymentCardRepository.save(paymentCard).getId();
    }

    @Transactional
    public void activateCard(Long cardId) {
        var card = findCardByIdOrThrow(cardId);
        if (card.getStatus() == CardStatus.ACTIVE) {
            throw new CardOperationException("Card is already active");
        }
        card.setStatus(CardStatus.ACTIVE);
    }

    @Transactional
    public void blockCard(Long cardId) {
        var card = findCardByIdOrThrow(cardId);
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new CardOperationException("Card cannot be blocked unless it is active");
        }
        card.setStatus(CardStatus.BLOCKED);
    }

    @Transactional
    public void depositToCard(Long cardId, BigDecimal amount) {
        var card = findCardByIdOrThrow(cardId);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new CardOperationException("Deposits can only be made to an active card");
        }
        var newBalance = card.getBalance().add(amount).setScale(2, RoundingMode.HALF_EVEN);
        card.setBalance(newBalance);
    }

    @Transactional
    public void withdrawFromCard(Long cardId, BigDecimal amount) {
        var card = findCardByIdOrThrow(cardId);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        }
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new CardOperationException("Withdrawals can only be made from an active card");
        }
        if (amount.compareTo(card.getBalance()) > 0) {
            throw new CardOperationException("Insufficient funds for withdrawal");
        }
        var newBalance = card.getBalance().subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
        card.setBalance(newBalance);
    }

    @Transactional(readOnly = true)
    public PaymentCardDto getCardById(Long cardId) {
        var card = findCardByIdOrThrow(cardId);
        return new PaymentCardDto(card.getId(), card.getCardNumber(),
                card.getBalance(), card.getStatus());
    }

    private PaymentCard findCardByIdOrThrow(Long cardId) {
        return paymentCardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }
}
