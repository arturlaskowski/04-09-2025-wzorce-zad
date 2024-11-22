package wzorce.adapter;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class RelationshipBetweenClassesTests {

    @Test
    void newPaymentServiceShouldNotDependDirectlyOnLegacyPaymentService() {
        var importedClasses = new ClassFileImporter().importPackages("wzorce.adapter");

        var rule = noClasses()
                .that().areAssignableTo(NewPaymentService.class)
                .should().dependOnClassesThat().areAssignableTo(LegacyPaymentService.class)
                .because("Jeśli wykorzystamy wzorzec Adapter, nie będziemy mieli bezpośredniej zależności między obiektami NewPaymentService i LegacyPaymentService.");

        rule.check(importedClasses);
    }

    @Test
    void paymentControllerShouldNotDependDirectlyOnLegacyPaymentService() {
        var importedClasses = new ClassFileImporter().importPackages("wzorce.adapter");

        var rule = noClasses()
                .that().areAssignableTo(PaymentController.class)
                .should().dependOnClassesThat().areAssignableTo(LegacyPaymentService.class)
                .because("Jeśli wykorzystamy wzorzec Adapter, nie będziemy mieli bezpośredniej zależności między obiektami PaymentController i LegacyPaymentService.");

        rule.check(importedClasses);
    }
}
