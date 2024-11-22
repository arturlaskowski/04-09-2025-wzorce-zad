package wzorce.decorator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static wzorce.decorator.TariffType.*;

@Component
class TariffFactory {

    private final BigDecimal baseKmRate;
    static final String STANDARD_NAME = "Taryfa 2025";

    public TariffFactory(@Value("${tariff.base-km-rate:3.00}") String baseKmRate) {
        this.baseKmRate = new BigDecimal(baseKmRate);
    }

    public Tariff createTariff(LocalDateTime time) {
        Tariff tariff = new BaseTariff(STANDARD_NAME, baseKmRate);

        // Sprawdź czy piątek
        if (time.getDayOfWeek() == DayOfWeek.FRIDAY) {
            tariff = new TariffDecorator(tariff, FRIDAY.getTariffName(), FRIDAY.getDiscountValue());
        }

        //  Sprawdź czy happy hours
        if (time.getHour() >= 14 && time.getHour() <= 16) {
            tariff = new TariffDecorator(tariff, HAPPY_HOURS.getTariffName(), HAPPY_HOURS.getDiscountValue());
        }

        //  Sprawdź czy Sylwestery
        if (time.getMonthValue() == 12 && time.getDayOfMonth() == 31) {
            tariff = new TariffDecorator(tariff, NEW_YEARS_EVE.getTariffName(), NEW_YEARS_EVE.getDiscountValue());
        }

        return tariff;
    }
}
