package wzorce.cqrs.domain.exception;


import wzorce.cqrs.domain.CouponId;

public class CouponNotActiveException extends RuntimeException {
    public CouponNotActiveException(CouponId couponId) {
        super("Coupon " + couponId.id() + " must be active to perform this operation.");
    }
}
