package wzorce.cqrs.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Embeddable
@EqualsAndHashCode
@ToString
public class CouponId {

    public static CouponId newOne() {
        return new CouponId(UUID.randomUUID());
    }

    private UUID couponId;

    public CouponId(UUID uuid) {
        this.couponId = uuid;
    }

    protected CouponId() {
    }

    public UUID id() {
        return couponId;
    }
}
