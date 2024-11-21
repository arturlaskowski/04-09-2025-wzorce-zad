package wzorce.oop;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentCardAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PaymentCardRepository paymentCardRepository;

    @AfterEach
    void cleanUp() {
        paymentCardRepository.deleteAll();
    }

    @Test
    @DisplayName("Given request to create a payment card, when request is sent, then correct URI is returned with HTTP 201 status")
    void givenRequestToCreatePaymentCard_whenRequestIsSent_thenCorrectUriAndHttp201Returned() {
        // given
        var createDto = new CreatePaymentCardDto("1234567890123456", new BigDecimal("222.22"));

        // when
        var postResponse = restTemplate.postForEntity(getBasePaymentCardsUrl(), new HttpEntity<>(createDto), Long.class);

        //then
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getHeaders().getLocation()).isNotNull();

        var getResponse = restTemplate.getForEntity(postResponse.getHeaders().getLocation(), PaymentCardDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("cardNumber", createDto.cardNumber())
                .hasFieldOrPropertyWithValue("balance", createDto.initialBalance())
                .hasFieldOrPropertyWithValue("status", CardStatus.ACTIVE)
                .extracting("id").isNotNull();
    }

    @Test
    @DisplayName("Given request to block a card, when request is sent, then HTTP 204 status received")
    void givenRequestToBlockCard_whenRequestIsSent_thenHttp204Received() {
        // given
        var createDto = new CreatePaymentCardDto("1234567890123456", new BigDecimal("222.22"));
        var cardId = createCardByApi(createDto);

        // when
        var blockResponse = restTemplate.postForEntity(
                getBasePaymentCardsUrl() + "/" + cardId + "/block",
                null,
                Void.class);

        // then
        assertThat(blockResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var getResponse = restTemplate.getForEntity(getBasePaymentCardsUrl() + "/" + cardId, PaymentCardDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("cardNumber", createDto.cardNumber())
                .hasFieldOrPropertyWithValue("balance", createDto.initialBalance())
                .hasFieldOrPropertyWithValue("status", CardStatus.BLOCKED)
                .hasFieldOrPropertyWithValue("id", cardId);
    }

    @Test
    @DisplayName("Given request to activate a card, when request is sent, then HTTP 204 status received")
    void givenRequestToActivateCard_whenRequestIsSent_thenHttp204Received() {
        // given
        var createDto = new CreatePaymentCardDto("1234567890123456", new BigDecimal("222.22"));
        var cardId = createCardByApi(createDto);
        blockCardByApi(cardId);

        // when
        var activeResponse = restTemplate.postForEntity(
                getBasePaymentCardsUrl() + "/" + cardId + "/activation",
                null,
                Void.class);

        // then
        assertThat(activeResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var getResponse = restTemplate.getForEntity(getBasePaymentCardsUrl() + "/" + cardId, PaymentCardDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("cardNumber", createDto.cardNumber())
                .hasFieldOrPropertyWithValue("balance", createDto.initialBalance())
                .hasFieldOrPropertyWithValue("status", CardStatus.ACTIVE)
                .hasFieldOrPropertyWithValue("id", cardId);
    }

    @Test
    @DisplayName("Given request to deposit to a card, when request is sent, then HTTP 204 status received")
    void givenRequestToDepositFromCard_whenRequestIsSent_thenHttp204Received() {
        // given
        var createDto = new CreatePaymentCardDto("1234567890123456", BigDecimal.ZERO);
        var cardId = createCardByApi(createDto);
        var transactionDto = new TransactionDto(new BigDecimal("100.00"));

        // when
        var responseWithdrawal = restTemplate.postForEntity(
                getBasePaymentCardsUrl() + "/" + cardId + "/deposit",
                new HttpEntity<>(transactionDto),
                Void.class);

        // then
        assertThat(responseWithdrawal.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var getResponse = restTemplate.getForEntity(getBasePaymentCardsUrl() + "/" + cardId, PaymentCardDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("cardNumber", createDto.cardNumber())
                .hasFieldOrPropertyWithValue("balance", transactionDto.amount())
                .hasFieldOrPropertyWithValue("status", CardStatus.ACTIVE)
                .hasFieldOrPropertyWithValue("id", cardId);
    }

    @Test
    @DisplayName("Given request to withdrawal from a card, when request is sent, then HTTP 204 status received")
    void givenRequestToWithdrawalFromCard_whenRequestIsSent_thenHttp204Received() {
        // given
        var createDto = new CreatePaymentCardDto("1234567890123456", BigDecimal.ZERO);
        var cardId = createCardByApi(createDto);
        var depositDto = new TransactionDto(new BigDecimal("100.00"));
        depositByApi(cardId, depositDto);
        var withdrawalDto = new TransactionDto(new BigDecimal("50.00"));

        // when
        var responseWithdrawal = restTemplate.postForEntity(
                getBasePaymentCardsUrl() + "/" + cardId + "/withdrawal",
                new HttpEntity<>(withdrawalDto),
                Void.class);

        // then
        assertThat(responseWithdrawal.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var getResponse = restTemplate.getForEntity(getBasePaymentCardsUrl() + "/" + cardId, PaymentCardDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("cardNumber", createDto.cardNumber())
                .hasFieldOrPropertyWithValue("balance", depositDto.amount().subtract(withdrawalDto.amount()))
                .hasFieldOrPropertyWithValue("status", CardStatus.ACTIVE)
                .hasFieldOrPropertyWithValue("id", cardId);
    }

    @Test
    @DisplayName("Given withdrawal amount exceeding balance, when request is sent, then receive HTTP 400 Bad Request")
    void givenWithdrawalAmountExceedingBalance_whenRequestIsSent_thenReceiveHttp400BadRequest() {
        // given
        var createDto = new CreatePaymentCardDto("1234567890123456", new BigDecimal("50.00"));
        var cardId = createCardByApi(createDto);
        var withdrawalDto = new TransactionDto(new BigDecimal("100.00"));

        // when
        var response = restTemplate.postForEntity(
                getBasePaymentCardsUrl() + "/" + cardId + "/withdrawal",
                new HttpEntity<>(withdrawalDto),
                ApiErrorResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST); // Assuming 400 is returned for this scenario
        assertThat(response.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .extracting("message")
                .asString()
                .contains("Insufficient funds for withdrawal");
    }

    @Test
    @DisplayName("Given request to active non-existent card, when request is sent, then HTTP 404 status received")
    void givenRequestToActiveForNonExistentCard_whenRequestIsSent_thenHttp404Received() {
        // given
        var notExistingCardId = -1L;

        // when
        var activeResponse = restTemplate.postForEntity(
                getBasePaymentCardsUrl() + "/" + notExistingCardId + "/activation",
                null,
                ApiErrorResponse.class);

        // then
        assertThat(activeResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(activeResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .extracting("message")
                .asString()
                .contains("Card not found");
    }

    @Test
    @DisplayName("Given request to activate a card in an invalid state, when request is sent, then receive HTTP 400 Bad Request")
    void givenCardActivationRequestInInvalidState_whenRequestIsSent_thenReceiveHttp400BadRequest() {
        // given
        var createDto = new CreatePaymentCardDto("1234567890123456", BigDecimal.ZERO);
        var cardId = createCardByApi(createDto);

        // when
        var activeResponse = restTemplate.postForEntity(
                getBasePaymentCardsUrl() + "/" + cardId + "/activation",
                null,
                ApiErrorResponse.class);

        // then
        assertThat(activeResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(activeResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .extracting("message")
                .asString()
                .contains("Card is already active");
    }

    private void blockCardByApi(Long cardId) {
        var response = restTemplate.postForEntity(getBasePaymentCardsUrl() + "/" + cardId + "/block", null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private Long createCardByApi(CreatePaymentCardDto createDto) {
        var postResponse = restTemplate.postForEntity(getBasePaymentCardsUrl(), new HttpEntity<>(createDto), Long.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getHeaders().getLocation()).isNotNull();
        return Long.valueOf(UriComponentsBuilder.fromUri(postResponse.getHeaders().getLocation()).build()
                .getPathSegments().getLast());
    }

    private void depositByApi(Long cardId, TransactionDto transactionDto) {
        var response = restTemplate.postForEntity(getBasePaymentCardsUrl() + "/" + cardId + "/deposit", new HttpEntity<>(transactionDto), Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private String getBasePaymentCardsUrl() {
        return "http://localhost:" + port + "/payment-cards";
    }
}