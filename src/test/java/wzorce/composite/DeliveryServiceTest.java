package wzorce.composite;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.assertj.core.api.Assertions.assertThat;
import static wzorce.composite.DeliveryStatus.PENDING;

@SpringBootTest
class DeliveryServiceTest {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @AfterEach
    void cleanUp() {
        deliveryRepository.deleteAll();
    }

    @Test
    void prepareDeliveryTest() {
        //given
        var transactionId = "99991111";
        var address = "Ferdynant Kiepski, Ćwiartki 3/4, 50-003 Wrocław";

        var book1 = new Product("W pustyni i w puszczy 3", new BigDecimal("25.00"), 1);
        var book2 = new Product("Calineczka", new BigDecimal("12.00"), 1);
        var speaker = new Product("Głośnik fajny", new BigDecimal("589.22"), 3);
        var jacket = new Product("Kurtka zimowa", new BigDecimal("400.00"), 1);

        // Przesyłka powinna zawierać pudełko z:
        // - Kurtką luzem
        // - Dwoma książkami w osobnym pudełku
        // - Głośnikiem w osobnym pudełku

        var compositePackage = new Box(
                jacket,
                new Box(book1, book2),
                new Box(speaker));

        //given
        deliveryService.prepareDelivery(transactionId, compositePackage, address);

        //then
        var delivery = deliveryRepository.findById(transactionId).orElseThrow();
        assertThat(delivery)
                .hasFieldOrPropertyWithValue("transactionId", transactionId)
                .hasFieldOrPropertyWithValue("address", address)
                .hasFieldOrPropertyWithValue("productsCost", new BigDecimal("1026.22"))
                .hasFieldOrPropertyWithValue("boxWeight", 6)
                .hasFieldOrPropertyWithValue("status", PENDING);
    }

    @Test
    void boxShouldNotDependDirectlyOnProduct() {
        var importedClasses = new ClassFileImporter().importPackages("wzorce.composite");

        var rule = noClasses()
                .that().areAssignableTo(Box.class)
                .should().dependOnClassesThat().areAssignableTo(Product.class)
                .because("Jeśli wykorzystamy wzorzec Composite, nie będziemy mieli bezpośredniej zależności między obiektami Box i Product.");

        rule.check(importedClasses);
    }
}
