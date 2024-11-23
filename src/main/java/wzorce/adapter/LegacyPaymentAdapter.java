package wzorce.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "payment.system", havingValue = "legacy")
@RequiredArgsConstructor
class LegacyPaymentAdapter implements PaymentProcessor {

    private final LegacyPaymentService legacyPaymentService;

    @Override
    public void processPayment(NewSystemPaymentDto newSystemPaymentDto) {
        var legacyPaymentDto = new LegacyPaymentDto(newSystemPaymentDto.accountNumber()
                , newSystemPaymentDto.transactionId(), newSystemPaymentDto.amount().intValue());

        if (Currency.PLN != newSystemPaymentDto.currency()) {
            throw new UnsupportedCurrencyException(newSystemPaymentDto.currency());
        }

        legacyPaymentService.makePayment(legacyPaymentDto);
    }
}