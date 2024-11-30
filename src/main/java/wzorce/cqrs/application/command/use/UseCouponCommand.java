package wzorce.cqrs.application.command.use;

import jakarta.validation.constraints.NotNull;
import wzorce.cqrs.common.Command;
import wzorce.cqrs.domain.CouponId;

public record UseCouponCommand(
        @NotNull CouponId couponId) implements Command {
}

