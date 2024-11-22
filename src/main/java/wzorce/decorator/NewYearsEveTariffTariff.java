package wzorce.decorator;

import java.math.BigDecimal;

import static wzorce.decorator.TariffType.NEW_YEARS_EVE;

class NewYearsEveTariffTariff extends BaseTariff {

    public NewYearsEveTariffTariff(String standardName, BigDecimal baseKmRate) {
        super(standardName, baseKmRate);
    }

    @Override
    public String getName() {
        return String.join(NAME_DELIMITER, super.name, NEW_YEARS_EVE.getTariffName());
    }

    @Override
    public BigDecimal getKmRate() {
        return super.kmRate.subtract(NEW_YEARS_EVE.getDiscountValue());
    }
}
