package wzorce.decorator;

import java.math.BigDecimal;

import static wzorce.decorator.TariffType.HAPPY_HOURS;
import static wzorce.decorator.TariffType.NEW_YEARS_EVE;

class HappyHoursNewYearsEveTariff extends BaseTariff {

    public HappyHoursNewYearsEveTariff(String standardName, BigDecimal baseKmRate) {
        super(standardName, baseKmRate);
    }

    @Override
    public String getName() {
        return String.join(NAME_DELIMITER, super.name, HAPPY_HOURS.getTariffName(), NEW_YEARS_EVE.getTariffName());
    }

    @Override
    public BigDecimal getKmRate() {
        return super.kmRate.subtract(HAPPY_HOURS.getDiscountValue()).subtract(NEW_YEARS_EVE.getDiscountValue());
    }
}
