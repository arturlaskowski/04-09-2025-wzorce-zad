package wzorce.cqrs.application.command.create;

import jakarta.validation.constraints.NotNull;
import wzorce.cqrs.common.Command;
import wzorce.cqrs.domain.CouponId;
import wzorce.cqrs.domain.NominalValue;

public record CreateCouponCommand(
        @NotNull CouponId couponId,
        @NotNull NominalValue nominalValue) implements Command {
}

