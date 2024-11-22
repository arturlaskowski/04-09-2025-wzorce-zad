package wzorce.decorator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
enum TariffType {
    FRIDAY("Friday", new BigDecimal("0.2")),
    HAPPY_HOURS("Happy Hours", new BigDecimal("0.3")),
    NEW_YEARS_EVE("New Years Eve", new BigDecimal("0.5"));

    private final String tariffName;
    private final BigDecimal discountValue;
}

