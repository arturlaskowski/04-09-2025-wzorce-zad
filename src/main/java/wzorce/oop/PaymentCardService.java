package wzorce.oop;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
class PaymentCardService {

    private final PaymentCardRepository paymentCardRepository;

    public Long createPaymentCard(CreatePaymentCardDto dto) {
        var paymentCard = new PaymentCard(dto.cardNumber(), new Money(dto.initialBalance()));
        return paymentCardRepository.save(paymentCard).getId();
    }

    @Transactional
    public void activateCard(Long cardId) {
        var card = findCardByIdOrThrow(cardId);
        card.activate();
    }

    @Transactional
    public void blockCard(Long cardId) {
        var card = findCardByIdOrThrow(cardId);
        card.block();
    }

    @Transactional
    public void depositToCard(Long cardId, BigDecimal amount) {
        var card = findCardByIdOrThrow(cardId);
        card.deposit(new Money(amount));
    }

    @Transactional
    public void withdrawFromCard(Long cardId, BigDecimal amount) {
        var card = findCardByIdOrThrow(cardId);
        card.withdraw(new Money(amount));
    }

    @Transactional(readOnly = true)
    public PaymentCardDto getCardById(Long cardId) {
        var card = findCardByIdOrThrow(cardId);
        return new PaymentCardDto(card.getId(), card.getCardNumber(),
                card.getBalance().amount(), card.getStatus());
    }

    private PaymentCard findCardByIdOrThrow(Long cardId) {
        return paymentCardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }


    // Metoda do przećwiczenia działania mechanizmu Optimistic Locking.
    public void withdrawFromCardNativeSql(Long cardId, BigDecimal amount) {
        var card = findCardByIdOrThrow(cardId);
        var moneyAmount = new Money(amount);

        if (!card.getBalance().isGreaterThanOrEqualTo(moneyAmount)) {
            throw new CardOperationException("Insufficient funds for withdrawal");
        }

        var balanceAfterWithdraw = card.getBalance().subtract(moneyAmount);

        int updatedRows = paymentCardRepository.updateBalanceWithOptimisticLock(cardId, balanceAfterWithdraw.amount());
        if (updatedRows == 0) {
            throw new OptimisticLockingFailureException("Failed to update card balance due to concurrent modification");
        }
    }
}
