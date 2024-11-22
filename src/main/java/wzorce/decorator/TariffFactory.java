package wzorce.decorator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
class TariffFactory {

    private final BigDecimal baseKmRate;
    static final String STANDARD_NAME = "Taryfa 2025";

    public TariffFactory(@Value("${tariff.base-km-rate:3.00}") String baseKmRate) {
        this.baseKmRate = new BigDecimal(baseKmRate);
    }

    public BaseTariff createTariff(LocalDateTime time) {
        boolean isFriday = false;
        boolean happyHours = false;
        boolean newYearsEve = false;

        // Sprawdź czy piątek
        if (time.getDayOfWeek() == DayOfWeek.FRIDAY) {
            isFriday = true;
        }

        //  Sprawdź czy happy hours
        if (time.getHour() >= 14 && time.getHour() <= 16) {
            happyHours = true;
        }

        //  Sprawdź czy Sylwestery
        if (time.getMonthValue() == 12 && time.getDayOfMonth() == 31) {
            newYearsEve = true;
        }

        if (isFriday && happyHours && newYearsEve) {
            return new FridayHappyHoursNewYearsEveTariff(STANDARD_NAME, baseKmRate);
        } else if (isFriday && newYearsEve) {
            return new FridayNewYearsEveTariff(STANDARD_NAME, baseKmRate);
        } else if (isFriday && happyHours) {
            return new FridayHappyHoursTariff(STANDARD_NAME, baseKmRate);
        } else if (happyHours && newYearsEve) {
            return new HappyHoursNewYearsEveTariff(STANDARD_NAME, baseKmRate);
        } else if (isFriday) {
            return new FridayTariffTariff(STANDARD_NAME, baseKmRate);
        } else if (happyHours) {
            return new HappyHoursTariffTariff(STANDARD_NAME, baseKmRate);
        } else if (newYearsEve) {
            return new NewYearsEveTariffTariff(STANDARD_NAME, baseKmRate);
        } else {
            return new BaseTariff(STANDARD_NAME, baseKmRate);
        }
    }
}
