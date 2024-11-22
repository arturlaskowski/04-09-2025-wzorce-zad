package wzorce.decorator;

import java.math.BigDecimal;

class BaseTariff implements Tariff {

    protected String name;
    protected BigDecimal kmRate;

    BaseTariff(String name, BigDecimal kmRate) {
        this.name = name;
        this.kmRate = kmRate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getKmRate() {
        return kmRate;
    }
}