package wzorce.cqrs.application;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wzorce.cqrs.application.dto.CouponDetailsDto;
import wzorce.cqrs.application.dto.CreateCouponDto;
import wzorce.cqrs.application.exception.CouponNotFoundException;
import wzorce.cqrs.domain.Coupon;
import wzorce.cqrs.domain.CouponId;

@Service
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponId createCoupon(CreateCouponDto createCouponDto) {
        var coupon = new Coupon(createCouponDto.nominalValue());
        return couponRepository.save(coupon).getId();
    }

    @Transactional
    public void useCoupon(CouponId couponId) {
        var coupon = findCouponById(couponId);
        coupon.use();
    }

    @Transactional
    public void deactivateCoupon(CouponId couponId) {
        var coupon = findCouponById(couponId);
        coupon.deactivate();
    }

    public CouponDetailsDto getCoupon(CouponId couponId) {
        return couponRepository.findById(couponId)
                .map(coupon -> new CouponDetailsDto(coupon.getId().id(), coupon.getStatus(), coupon.getNominalValue()))
                .orElseThrow(() -> new CouponNotFoundException(couponId));
    }

    private Coupon findCouponById(CouponId couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));
    }
}
