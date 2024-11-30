package wzorce.cqrs.domain.exception;


import wzorce.cqrs.domain.CouponId;

import java.time.LocalDate;

public class CouponNotValidException extends RuntimeException {

    public CouponNotValidException(CouponId couponId, LocalDate date) {
        super("Coupon " + couponId.id() + " is not valid on " + date);
    }
}