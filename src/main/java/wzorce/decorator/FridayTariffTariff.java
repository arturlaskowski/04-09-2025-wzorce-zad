package wzorce.decorator;

import java.math.BigDecimal;

import static wzorce.decorator.TariffType.FRIDAY;

class FridayTariffTariff extends BaseTariff {

    FridayTariffTariff(String name, BigDecimal kmRate) {
        super(name, kmRate);
    }

    @Override
    public String getName() {
        return String.join(NAME_DELIMITER, super.name, FRIDAY.getTariffName());
    }

    @Override
    public BigDecimal getKmRate() {
        return super.kmRate.subtract(FRIDAY.getDiscountValue());
    }
}
