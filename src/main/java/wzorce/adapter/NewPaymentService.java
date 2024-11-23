package wzorce.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "payment.system", havingValue = "new", matchIfMissing = true)
class NewPaymentService implements PaymentProcessor {

    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(NewSystemPaymentDto newSystemPaymentDto) {
        var payment = new Payment(newSystemPaymentDto.accountNumber(), newSystemPaymentDto.transactionId(),
                newSystemPaymentDto.amount(), newSystemPaymentDto.currency(), PaymentSystem.NEW);

        //call to new system

        paymentRepository.save(payment);
    }
}
