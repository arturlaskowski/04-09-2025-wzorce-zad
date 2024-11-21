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
        card.deposit(amount);
    }

    @Transactional
    public void withdrawFromCard(Long cardId, BigDecimal amount) {
        var card = findCardByIdOrThrow(cardId);
        card.withdraw(amount);
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
