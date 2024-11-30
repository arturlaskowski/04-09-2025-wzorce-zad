package wzorce.cqrs.application.command.deactivate;

import jakarta.validation.constraints.NotNull;
import wzorce.cqrs.common.Command;
import wzorce.cqrs.domain.CouponId;

public record DeactivateCouponCommand(
        @NotNull CouponId couponId) implements Command {
}

