package wzorce.factorymethod;

import java.math.BigDecimal;
import java.util.UUID;

interface Room {

    UUID getId();

    BigDecimal getPrice();

    void calculateRoomCost();
}
