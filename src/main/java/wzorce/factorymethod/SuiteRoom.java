package wzorce.factorymethod;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Getter
class SuiteRoom implements Room {

    private static final BigDecimal BEACH_ACCESS = BigDecimal.valueOf(50);

    private UUID id;
    private String name;
    private int roomSize;
    private boolean hasPrivateBeachAccess;
    private String additionalInfo;
    private BigDecimal minPrice;
    private BigDecimal price;

    public SuiteRoom(String name, int roomSize, boolean hasPrivateBeachAccess, String additionalInfo, BigDecimal minPrice) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.roomSize = roomSize;
        this.hasPrivateBeachAccess = hasPrivateBeachAccess;
        this.additionalInfo = additionalInfo;
        this.minPrice = minPrice;
    }

    @Override
    public void calculateRoomCost() {
        this.price = minPrice;

        if (hasPrivateBeachAccess) {
            this.price = this.price.add(BEACH_ACCESS);
        }
    }
}