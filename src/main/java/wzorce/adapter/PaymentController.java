package wzorce.adapter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class PaymentController {

    private final NewPaymentService newPaymentService;

    @PostMapping("/payments")
    void makePayment(@RequestBody @Valid NewSystemPaymentDto newSystemPaymentDto) {
        newPaymentService.processPayment(newSystemPaymentDto);
    }
}
