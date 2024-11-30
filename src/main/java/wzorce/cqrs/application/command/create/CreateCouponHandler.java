package wzorce.cqrs.application.command.create;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wzorce.cqrs.application.command.CouponRepository;
import wzorce.cqrs.common.CommandHandler;
import wzorce.cqrs.domain.Coupon;

@Service
@RequiredArgsConstructor
public class CreateCouponHandler implements CommandHandler<CreateCouponCommand> {

    private final CouponRepository couponRepository;

    @Override
    public void handle(CreateCouponCommand command) {
        var coupon = new Coupon(command.couponId(), command.nominalValue());
        couponRepository.save(coupon);
    }
}
