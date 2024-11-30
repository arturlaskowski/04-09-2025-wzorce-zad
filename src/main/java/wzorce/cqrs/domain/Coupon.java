package wzorce.cqrs.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wzorce.cqrs.domain.exception.CouponNotActiveException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //For JPA
public class Coupon {

    @Id
    private CouponId id;
    private CouponStatus status;
    private NominalValue nominalValue;

    @Version
    private int version;

    public Coupon(NominalValue nominalValue) {
        this.id = CouponId.newOne();
        this.status = CouponStatus.ACTIVE;
        this.nominalValue = nominalValue;
    }

    public void use() {
        if (this.status != CouponStatus.ACTIVE) {
            throw new CouponNotActiveException(id);
        }

        this.status = CouponStatus.USED;
    }

    public void deactivate() {
        if (this.status != CouponStatus.ACTIVE) {
            throw new CouponNotActiveException(id);
        }
        this.status = CouponStatus.DEACTIVATED;
    }
}
