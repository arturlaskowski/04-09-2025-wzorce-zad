package wzorce.decorator;

import java.math.BigDecimal;

import static wzorce.decorator.TariffType.*;

class FridayHappyHoursNewYearsEveTariff extends BaseTariff {

    public FridayHappyHoursNewYearsEveTariff(String standardName, BigDecimal baseKmRate) {
        super(standardName, baseKmRate);
    }

    @Override
    public String getName() {
        return String.join(NAME_DELIMITER, super.name, FRIDAY.getTariffName(), HAPPY_HOURS.getTariffName(), NEW_YEARS_EVE.getTariffName());
    }

    @Override
    public BigDecimal getKmRate() {
        return super.kmRate.subtract(FRIDAY.getDiscountValue()).subtract(HAPPY_HOURS.getDiscountValue()).subtract(NEW_YEARS_EVE.getDiscountValue());
    }
}
