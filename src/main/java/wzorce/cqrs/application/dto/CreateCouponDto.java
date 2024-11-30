package wzorce.cqrs.application.dto;

import jakarta.validation.constraints.NotNull;
import wzorce.cqrs.domain.NominalValue;

public record CreateCouponDto(@NotNull NominalValue nominalValue) {
}
