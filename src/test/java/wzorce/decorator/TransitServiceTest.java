package wzorce.decorator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static wzorce.decorator.BaseTariff.NAME_DELIMITER;
import static wzorce.decorator.TariffFactory.STANDARD_NAME;
import static wzorce.decorator.TariffType.*;
import static wzorce.decorator.TransitStatus.COMPLETED;

@SpringBootTest
class TransitServiceTest {

    @Autowired
    private TransitService transitService;

    @Autowired
    private TransitRepository transitRepository;

    private static final int DISTANCE = 10;

    @AfterEach
    void cleanUp() {
        transitRepository.deleteAll();
    }

    @Test
    void baseRateTest() {
        //given
        var normalDay = LocalDateTime.of(2023, Month.APRIL, 15, 10, 0);
        var expectedCostFor10Km = new BigDecimal("30.00");
        var transitId = transitService.startTransit(UUID.randomUUID(), normalDay);

        //when
        transitService.finishTransit(transitId, DISTANCE);

        //given
        var transit = transitRepository.findById(transitId).orElseThrow();
        assertThat(transit)
                .hasFieldOrPropertyWithValue("tariffName", STANDARD_NAME)
                .hasFieldOrPropertyWithValue("distance", DISTANCE)
                .hasFieldOrPropertyWithValue("totalCost", expectedCostFor10Km)
                .hasFieldOrPropertyWithValue("requestTime", normalDay)
                .hasFieldOrPropertyWithValue("status", COMPLETED);
    }

    @Test
    void tariffFridayTest() {
        //given
        var friday = LocalDateTime.of(2023, Month.MAY, 5, 10, 0);
        var expectedName = String.join(NAME_DELIMITER, STANDARD_NAME, FRIDAY.getTariffName());
        var expectedCostFor10Km = new BigDecimal("28.00");
        var transitId = transitService.startTransit(UUID.randomUUID(), friday);

        //when
        transitService.finishTransit(transitId, DISTANCE);

        //given
        var transit = transitRepository.findById(transitId).orElseThrow();
        assertThat(transit)
                .hasFieldOrPropertyWithValue("tariffName", expectedName)
                .hasFieldOrPropertyWithValue("distance", DISTANCE)
                .hasFieldOrPropertyWithValue("totalCost", expectedCostFor10Km)
                .hasFieldOrPropertyWithValue("requestTime", friday)
                .hasFieldOrPropertyWithValue("status", COMPLETED);
    }

    @Test
    void tariffHappyHoursTest() {
        //given
        var happyHour = LocalDateTime.of(2023, Month.APRIL, 15, 15, 0);
        var expectedName = String.join(NAME_DELIMITER, STANDARD_NAME, HAPPY_HOURS.getTariffName());
        var expectedCostFor10Km = new BigDecimal("27.00");
        var transitId = transitService.startTransit(UUID.randomUUID(), happyHour);

        //when
        transitService.finishTransit(transitId, DISTANCE);

        //given
        var transit = transitRepository.findById(transitId).orElseThrow();
        assertThat(transit)
                .hasFieldOrPropertyWithValue("tariffName", expectedName)
                .hasFieldOrPropertyWithValue("distance", DISTANCE)
                .hasFieldOrPropertyWithValue("totalCost", expectedCostFor10Km)
                .hasFieldOrPropertyWithValue("requestTime", happyHour)
                .hasFieldOrPropertyWithValue("status", COMPLETED);
    }

    @Test
    void tariffFridayAndHappyHoursTest() {
        //given
        var happyHour = LocalDateTime.of(2023, Month.MAY, 5, 15, 0);
        var expectedName = String.join(NAME_DELIMITER, STANDARD_NAME, FRIDAY.getTariffName(), HAPPY_HOURS.getTariffName());
        var expectedCostFor10Km = new BigDecimal("25.00");
        var transitId = transitService.startTransit(UUID.randomUUID(), happyHour);

        //when
        transitService.finishTransit(transitId, DISTANCE);

        //given
        var transit = transitRepository.findById(transitId).orElseThrow();
        assertThat(transit)
                .hasFieldOrPropertyWithValue("tariffName", expectedName)
                .hasFieldOrPropertyWithValue("distance", DISTANCE)
                .hasFieldOrPropertyWithValue("totalCost", expectedCostFor10Km)
                .hasFieldOrPropertyWithValue("requestTime", happyHour)
                .hasFieldOrPropertyWithValue("status", COMPLETED);
    }

    @Test
    void tariffNewYearsEveTest() {
        //given
        var newYearsEve = LocalDateTime.of(2023, Month.DECEMBER, 31, 10, 0);
        var expectedName = String.join(NAME_DELIMITER, STANDARD_NAME, NEW_YEARS_EVE.getTariffName());
        var expectedCostFor10Km = new BigDecimal("25.00");
        var transitId = transitService.startTransit(UUID.randomUUID(), newYearsEve);

        //when
        transitService.finishTransit(transitId, DISTANCE);

        //given
        var transit = transitRepository.findById(transitId).orElseThrow();
        assertThat(transit)
                .hasFieldOrPropertyWithValue("tariffName", expectedName)
                .hasFieldOrPropertyWithValue("distance", DISTANCE)
                .hasFieldOrPropertyWithValue("totalCost", expectedCostFor10Km)
                .hasFieldOrPropertyWithValue("requestTime", newYearsEve)
                .hasFieldOrPropertyWithValue("status", COMPLETED);
    }

    @Test
    void tariffAllDiscountsTest() {
        //given
        var allDiscountsDay = LocalDateTime.of(2027, Month.DECEMBER, 31, 14, 0);
        var expectedName = String.join(NAME_DELIMITER, STANDARD_NAME, FRIDAY.getTariffName(),
                HAPPY_HOURS.getTariffName(), NEW_YEARS_EVE.getTariffName());
        var transitId = transitService.startTransit(UUID.randomUUID(), allDiscountsDay);
        var expectedCostFor10Km = new BigDecimal("20.00");

        //when
        transitService.finishTransit(transitId, DISTANCE);

        //given
        var transit = transitRepository.findById(transitId).orElseThrow();
        assertThat(transit)
                .hasFieldOrPropertyWithValue("tariffName", expectedName)
                .hasFieldOrPropertyWithValue("distance", DISTANCE)
                .hasFieldOrPropertyWithValue("totalCost", expectedCostFor10Km)
                .hasFieldOrPropertyWithValue("requestTime", allDiscountsDay)
                .hasFieldOrPropertyWithValue("status", COMPLETED);
    }
}