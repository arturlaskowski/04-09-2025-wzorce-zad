package wzorce.cqrs.application.command.deactivate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wzorce.cqrs.application.command.CouponRepository;
import wzorce.cqrs.application.exception.CouponNotFoundException;
import wzorce.cqrs.common.CommandHandler;

@Service
@RequiredArgsConstructor
public class DeactivateCouponHandler implements CommandHandler<DeactivateCouponCommand> {

    private final CouponRepository couponRepository;

    @Override
    @Transactional
    public void handle(DeactivateCouponCommand command) {
        var coupon = couponRepository.findById(command.couponId())
                .orElseThrow(() -> new CouponNotFoundException(command.couponId()));
        coupon.deactivate();
    }
}
