package wzorce.cqrs.domain.exception;


import wzorce.cqrs.domain.CouponId;

public class CouponAlreadyUsedException extends RuntimeException {
    public CouponAlreadyUsedException(CouponId couponId) {
        super("Coupon " + couponId.id() + " has already been used and cannot be used again.");
    }
}
