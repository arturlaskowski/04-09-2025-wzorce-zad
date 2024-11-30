package wzorce.cqrs.application.exception;


import wzorce.cqrs.domain.CouponId;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException(CouponId couponId) {
        super("Coupon with ID " + couponId.id() + " not found");
    }
}
