package wzorce.cqrs;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;
import wzorce.cqrs.application.dto.CouponDetailsDto;
import wzorce.cqrs.application.dto.CreateCouponDto;
import wzorce.cqrs.domain.CouponStatus;
import wzorce.cqrs.domain.NominalValue;
import wzorce.cqrs.web.CouponController;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CouponAcceptanceZad1Test {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void couponAcceptanceTest() {
        // given
        var createCouponDto = new CreateCouponDto(NominalValue.TWENTY);

        //when
        var creatResponse = restTemplate.postForEntity(getBaseCouponsUrl(), createCouponDto, Void.class);

        //then
        assertThat(creatResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(creatResponse.getHeaders().getLocation()).isNotNull();

        // get after created
        var getResponse = restTemplate.getForEntity(creatResponse.getHeaders().getLocation(), CouponDetailsDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("nominalValue", createCouponDto.nominalValue())
                .hasFieldOrPropertyWithValue("status", CouponStatus.ACTIVE)
                .extracting("id").isNotNull();

        //use - coupon
        var couponId = UUID.fromString(UriComponentsBuilder.fromUri(creatResponse.getHeaders().getLocation()).build()
                .getPathSegments().getLast());
        // when
        var useResponse = restTemplate.postForEntity(getBaseCouponsUrl() + "/" + couponId + "/use",
                createCouponDto, Void.class);

        //then
        assertThat(useResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // get after used
        var getAfterUsedResponse = restTemplate.getForEntity(creatResponse.getHeaders().getLocation(), CouponDetailsDto.class);
        assertThat(getAfterUsedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getAfterUsedResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("nominalValue", createCouponDto.nominalValue())
                .hasFieldOrPropertyWithValue("status", CouponStatus.USED)
                .extracting("id").isNotNull();
    }

    String getBaseCouponsUrl() {
        return "http://localhost:" + port + "/coupons";
    }

    @Test
    void controllerShouldBeIndependentOfService() {
        var importedClasses = new ClassFileImporter().importPackages("wzorce.observer");

        var rule = ArchRuleDefinition.noClasses()
                .that().areAssignableTo(CouponController.class)
                .should().dependOnClassesThat().haveSimpleNameEndingWith("Service")
                .because("Jeśli zastosujemy wzorzec Mediator, " +
                        "nie będzie bezpośredniej zależności między kontrolerem a serwisem.");

        rule.check(importedClasses);
    }
}