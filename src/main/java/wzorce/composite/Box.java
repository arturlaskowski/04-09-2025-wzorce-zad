
package wzorce.composite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Box {

    private final List<Product> products = new ArrayList<>();

    public Box(Product... products) {
        this.products.addAll(Arrays.asList(products));
    }

    public BigDecimal calculatePrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : products) {
            total = total.add(product.getPrice());
        }
        return total;
    }

    public int calculateWeight() {
        int totalWeight = 0;
        for (Product product : products) {
            totalWeight += product.getWeight();
        }
        return totalWeight;
    }
}

