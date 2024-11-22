package wzorce.decorator;

import java.math.BigDecimal;

import static wzorce.decorator.TariffType.FRIDAY;
import static wzorce.decorator.TariffType.HAPPY_HOURS;

class FridayHappyHoursTariff extends BaseTariff {

    public FridayHappyHoursTariff(String standardName, BigDecimal baseKmRate) {
        super(standardName, baseKmRate);
    }

    @Override
    public String getName() {
        return String.join(NAME_DELIMITER, super.name, FRIDAY.getTariffName(), HAPPY_HOURS.getTariffName());
    }

    @Override
    public BigDecimal getKmRate() {
        return super.kmRate.subtract(FRIDAY.getDiscountValue()).subtract(HAPPY_HOURS.getDiscountValue());
    }
}
