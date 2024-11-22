package wzorce.oop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PaymentCardConcurrencyJpaTests {

    @Autowired
    private PaymentCardService paymentCardService;

    @Autowired
    private PaymentCardRepository paymentCardRepository;

    @AfterEach
    void cleanUp() {
        paymentCardRepository.deleteAll();
    }

    @RepeatedTest(10)
    void concurrentWithdrawalTest() throws InterruptedException {
        //given
        var initialBalance = new BigDecimal("400");
        var createPaymentCardDto = new CreatePaymentCardDto("1234567890123456", initialBalance);
        var paymentCardId = paymentCardService.createPaymentCard(createPaymentCardDto);
        var successCount = new AtomicInteger(0);

        // when
        Thread thread1 = new Thread(() -> {
            try {
                paymentCardService.withdrawFromCard(paymentCardId, new BigDecimal("300"));
                successCount.incrementAndGet();
            } catch (Exception e) {
                System.out.println("Nieudana wypłata: " + e.getMessage());
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                paymentCardService.withdrawFromCard(paymentCardId, new BigDecimal("200"));
                successCount.incrementAndGet();
            } catch (Exception e) {
                System.out.println("Nieudana wypłata: " + e.getMessage());
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        //then
        var balanceAfterWithdraws = paymentCardService.getCardById(paymentCardId).balance();
        System.out.println("Aktualny balans to: " + balanceAfterWithdraws);

        assertTrue(balanceAfterWithdraws.compareTo(new BigDecimal("100")) == 0 ||
                        balanceAfterWithdraws.compareTo(new BigDecimal("200")) == 0,
                "Saldo po wypłatach powinno wynosić albo 100 albo 200");

        assertEquals(1, successCount.get(), "Tylko jedna wypłata powinna się powieść");
    }
}
