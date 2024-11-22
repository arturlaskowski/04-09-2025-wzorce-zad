
package wzorce.composite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Box implements PackageComponent {

    private final List<PackageComponent> packageComponents = new ArrayList<>();

    public Box(PackageComponent... packageComponents) {
        this.packageComponents.addAll(Arrays.asList(packageComponents));
    }

    @Override
    public BigDecimal calculatePrice() {
        return packageComponents.stream()
                .map(PackageComponent::calculatePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public int calculateWeight() {
        return packageComponents.stream()
                .mapToInt(PackageComponent::calculateWeight)
                .sum();
    }
}

