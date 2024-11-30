package wzorce.cqrs.web;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;
import wzorce.cqrs.application.query.CouponDetailsDto;
import wzorce.cqrs.domain.CouponStatus;
import wzorce.cqrs.domain.NominalValue;

import java.util.UUID;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
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

        var couponId = UUID.fromString(UriComponentsBuilder.fromUri(creatResponse.getHeaders().getLocation()).build()
                .getPathSegments().getLast());

        // get after created
        var getResponse = restTemplate.getForEntity(getBaseCouponsUrl() + "/" + couponId, CouponDetailsDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("nominalValue", createCouponDto.nominalValue())
                .hasFieldOrPropertyWithValue("status", CouponStatus.ACTIVE)
                .extracting("id").isNotNull();

        //use - coupon
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

        //deactivate - coupon
        // when
        var deactivatedResponse = restTemplate.postForEntity(getBaseCouponsUrl() + "/" + couponId + "/deactivate",
                createCouponDto, ApiError.class);

        //then
        assertThat(deactivatedResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(deactivatedResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .extracting("message")
                .asString()
                .contains("Coupon " + couponId + " must be active to perform this operation");
    }

    String getBaseCouponsUrl() {
        return "http://localhost:" + port + "/coupons";
    }

    @Test
    void couponControllerShouldBeIndependentOfCouponService() {
        var importedClasses = new ClassFileImporter().importPackages("wzorce.cqrs");

        var rule = noClasses()
                .that().areAssignableTo(CouponController.class)
                .should().dependOnClassesThat().haveSimpleNameEndingWith("CouponService")
                .because("Jeśli zastosujemy wzorzec Mediator, " +
                        "nie będzie bezpośredniej zależności między CouponController a CouponService.");

        rule.check(importedClasses);
    }
}