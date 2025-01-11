package wzorce.cqrs;

import jakarta.validation.constraints.NotNull;
import wzorce.cqrs.domain.NominalValue;

record CreateCouponDto(@NotNull NominalValue nominalValue) {
}
