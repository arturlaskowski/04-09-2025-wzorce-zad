package wzorce.cqrs.application.query;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wzorce.cqrs.application.exception.CouponNotFoundException;
import wzorce.cqrs.domain.CouponId;

@Service
@AllArgsConstructor
public class CouponQueryService {

    private final CouponQueryRepository couponQueryRepository;

    public CouponDetailsDto getCoupon(CouponId couponId) {
        return couponQueryRepository.findById(couponId)
                .map(coupon -> new CouponDetailsDto(coupon.getId().id(), coupon.getStatus(), coupon.getNominalValue()))
                .orElseThrow(() -> new CouponNotFoundException(couponId));
    }
}
