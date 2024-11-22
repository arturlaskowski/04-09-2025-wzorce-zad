package wzorce.decorator;

import java.math.BigDecimal;

import static wzorce.decorator.TariffType.FRIDAY;
import static wzorce.decorator.TariffType.NEW_YEARS_EVE;

class FridayNewYearsEveTariff extends BaseTariff {

    public FridayNewYearsEveTariff(String standardName, BigDecimal baseKmRate) {
        super(standardName, baseKmRate);
    }

    @Override
    public String getName() {
        return String.join(NAME_DELIMITER, super.name, FRIDAY.getTariffName(), NEW_YEARS_EVE.getTariffName());
    }

    @Override
    public BigDecimal getKmRate() {
        return super.kmRate.subtract(FRIDAY.getDiscountValue().subtract(NEW_YEARS_EVE.getDiscountValue()));
    }
}
