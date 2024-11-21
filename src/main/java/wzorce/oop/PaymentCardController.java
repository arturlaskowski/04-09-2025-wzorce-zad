package wzorce.oop;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payment-cards")
class PaymentCardController {

    private final PaymentCardService paymentCardService;

    public PaymentCardController(PaymentCardService paymentCardService) {
        this.paymentCardService = paymentCardService;
    }

    @PostMapping
    public ResponseEntity<Long> createCard(@RequestBody CreatePaymentCardDto dto) {
        Long cardId = paymentCardService.createPaymentCard(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{cardId}/activation")
    public ResponseEntity<Void> activateCard(@PathVariable Long cardId) {
        paymentCardService.activateCard(cardId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cardId}/block")
    public ResponseEntity<Void> blockCard(@PathVariable Long cardId) {
        paymentCardService.blockCard(cardId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cardId}/deposit")
    public ResponseEntity<Void> depositToCard(@PathVariable Long cardId, @Valid @RequestBody TransactionDto transactionDto) {
        paymentCardService.depositToCard(cardId, transactionDto.amount());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cardId}/withdrawal")
    public ResponseEntity<Void> withdrawFromCard(@PathVariable Long cardId, @Valid @RequestBody TransactionDto transactionDto) {
        paymentCardService.withdrawFromCard(cardId, transactionDto.amount());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<PaymentCardDto> getCardById(@PathVariable Long cardId) {
        var cardDto = paymentCardService.getCardById(cardId);
        return ResponseEntity.ok(cardDto);
    }
}

