package wzorce.decorator;

import java.math.BigDecimal;

import static wzorce.decorator.TariffType.HAPPY_HOURS;

class HappyHoursTariffTariff extends BaseTariff {

    HappyHoursTariffTariff(String name, BigDecimal kmRate) {
        super(name, kmRate);
    }

    @Override
    public String getName() {
        return String.join(NAME_DELIMITER, super.name, HAPPY_HOURS.getTariffName());
    }

    @Override
    public BigDecimal getKmRate() {
        return super.kmRate.subtract(HAPPY_HOURS.getDiscountValue());
    }
}
