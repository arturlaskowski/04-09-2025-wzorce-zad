package wzorce.cqrs.application;

import org.springframework.data.repository.CrudRepository;
import wzorce.cqrs.domain.Coupon;
import wzorce.cqrs.domain.CouponId;

public interface CouponRepository extends CrudRepository<Coupon, CouponId> {
}
