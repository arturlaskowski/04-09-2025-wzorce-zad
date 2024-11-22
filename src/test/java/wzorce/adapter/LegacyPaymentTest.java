package wzorce.adapter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "payment.system=legacy", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LegacyPaymentTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PaymentRepository paymentRepository;

    @AfterEach
    void cleanUp() {
        paymentRepository.deleteAll();
    }

    @Test
    void makePaymentByLegacySystem() {
        //given
        var paymentDto = new NewSystemPaymentDto("1111111", "222222222", new BigDecimal("100.00"), Currency.PLN);

        //when
        var postResponse = restTemplate.postForEntity(getBasePaymentsUrl(), new HttpEntity<>(paymentDto), Void.class);

        //then
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        var payment = paymentRepository.findByTransactionId(paymentDto.transactionId());
        assertThat(payment)
                .hasFieldOrPropertyWithValue("transactionId", paymentDto.transactionId())
                .hasFieldOrPropertyWithValue("amount", paymentDto.amount())
                .hasFieldOrPropertyWithValue("currency", payment.getCurrency())
                .hasFieldOrPropertyWithValue("paymentSystem", PaymentSystem.LEGACY);
    }

    @Test
    void shouldThrowExceptionWhenCurrencyIsNotPLN() {
        // given
        var paymentDto = new NewSystemPaymentDto("1111111", "222222222", new BigDecimal("100.00"), Currency.USD);

        //when
        var postResponse = restTemplate.postForEntity(getBasePaymentsUrl(), new HttpEntity<>(paymentDto), ApiErrorResponse.class);

        //then
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(postResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .extracting("message")
                .asString()
                .contains("Currency USD is not supported");
    }

    private String getBasePaymentsUrl() {
        return "http://localhost:" + port + "/payments";
    }
}