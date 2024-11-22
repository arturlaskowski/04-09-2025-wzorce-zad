package wzorce.decorator;

import java.math.BigDecimal;

class BaseTariff {

    static final String NAME_DELIMITER = " & ";

    protected String name;
    protected BigDecimal kmRate;

    BaseTariff(String name, BigDecimal kmRate) {
        this.name = name;
        this.kmRate = kmRate;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getKmRate() {
        return kmRate;
    }

    public BigDecimal calculateCost(int distance) {
        return getKmRate().multiply(BigDecimal.valueOf(distance));
    }
}