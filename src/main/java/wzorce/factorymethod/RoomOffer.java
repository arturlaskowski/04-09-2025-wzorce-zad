package wzorce.factorymethod;

import java.math.BigDecimal;
import java.util.UUID;

record RoomOffer(
        UUID roomId,
        BigDecimal price) {
}
