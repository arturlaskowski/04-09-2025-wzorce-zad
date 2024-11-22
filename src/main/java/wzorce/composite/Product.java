package wzorce.composite;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
class Product {

    private String name;
    private BigDecimal price;
    private int weight;
}

