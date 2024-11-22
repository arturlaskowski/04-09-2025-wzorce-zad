package wzorce.decorator;

import java.math.BigDecimal;

class TariffDecorator implements Tariff {

    static final String NAME_DELIMITER = " & ";

    private final Tariff wrappedTariff;
    private final String tariffName;
    private final BigDecimal discountValue;

    TariffDecorator(Tariff wrappedTariff, String tariffName, BigDecimal discountValue) {
        this.wrappedTariff = wrappedTariff;
        this.tariffName = tariffName;
        this.discountValue = discountValue;
    }

    @Override
    public String getName() {
        return String.join(NAME_DELIMITER, wrappedTariff.getName(), tariffName);
    }

    @Override
    public BigDecimal getKmRate() {
        return wrappedTariff.getKmRate().subtract(discountValue);
    }
}
