package wzorce.decorator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class Transit {

    @Id
    private UUID id;
    private UUID customerId;
    private String tariffName;
    private int distance;
    private BigDecimal totalCost;
    private TransitStatus status;
    private LocalDateTime requestTime;

    public Transit(UUID customerId, LocalDateTime requestTime) {
        this.id = UUID.randomUUID();
        this.status = TransitStatus.STARTING;
        this.requestTime = requestTime;
        this.customerId = customerId;
    }

    void finish(int distance, String tariffName, BigDecimal totalCost) {
        this.distance = distance;
        this.tariffName = tariffName;
        this.totalCost = totalCost;
        this.status = TransitStatus.COMPLETED;
    }
}
