package wzorce.cqrs.application.query;


import wzorce.cqrs.domain.CouponStatus;
import wzorce.cqrs.domain.NominalValue;

import java.util.UUID;

public record CouponDetailsDto(
        UUID id,
        CouponStatus status,
        NominalValue nominalValue) {
}
