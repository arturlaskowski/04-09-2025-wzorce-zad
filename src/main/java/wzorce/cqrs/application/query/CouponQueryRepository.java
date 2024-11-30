package wzorce.cqrs.application.query;

import org.springframework.data.jpa.repository.JpaRepository;
import wzorce.cqrs.domain.Coupon;
import wzorce.cqrs.domain.CouponId;

interface CouponQueryRepository extends JpaRepository<Coupon, CouponId> {
}
