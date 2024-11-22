package wzorce.composite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Delivery {

    @Id
    private String transactionId;
    private BigDecimal productsCost;
    private int boxWeight;
    private String address;
    private DeliveryStatus status;

    public Delivery(String transactionId, BigDecimal productsCost,
                    int boxWeight, String address) {
        this.transactionId = transactionId;
        this.productsCost = productsCost;
        this.boxWeight = boxWeight;
        this.address = address;
        this.status = DeliveryStatus.PENDING;
    }
}
