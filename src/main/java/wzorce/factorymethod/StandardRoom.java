package wzorce.factorymethod;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Getter
class StandardRoom implements Room {

    private UUID id;
    private String name;
    private int costPerMeter;
    private BigDecimal price;
    private int roomSize;
    private String additionalInfo;

    public StandardRoom(String name, int roomSize,
                        int costPerMeter, String additionalInfo) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.roomSize = roomSize;
        this.costPerMeter = costPerMeter;
        this.additionalInfo = additionalInfo;
    }

    @Override
    public void calculateRoomCost() {
        this.price = BigDecimal.valueOf(costPerMeter).multiply(BigDecimal.valueOf(roomSize));
    }
}
