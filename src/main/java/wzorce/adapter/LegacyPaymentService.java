package wzorce.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
class LegacyPaymentService {

    private final PaymentRepository paymentRepository;

    public void makePayment(LegacyPaymentDto legacyPaymentDto) {
        var payment = new Payment(legacyPaymentDto.accountNumber(), legacyPaymentDto.transactionId(),
                new BigDecimal(legacyPaymentDto.amount()), Currency.PLN, PaymentSystem.LEGACY);

        //call to legacy system

        paymentRepository.save(payment);
    }
}
