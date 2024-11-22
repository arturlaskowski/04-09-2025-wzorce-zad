package wzorce.composite;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
class Product implements PackageComponent {

    private String name;
    private BigDecimal price;
    private int weight;

    @Override
    public BigDecimal calculatePrice() {
        return getPrice();
    }

    @Override
    public int calculateWeight() {
        return getWeight();
    }
}

