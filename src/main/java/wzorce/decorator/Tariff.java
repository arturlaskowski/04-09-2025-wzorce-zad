package wzorce.decorator;

import java.math.BigDecimal;

interface Tariff {

    String getName();

    BigDecimal getKmRate();

    default BigDecimal calculateCost(int distance) {
        return getKmRate().multiply(BigDecimal.valueOf(distance));
    }
}
