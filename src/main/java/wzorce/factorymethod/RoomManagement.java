package wzorce.factorymethod;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
class RoomManagement {

    private static final int STANDARD_ROOM_COST_PER_METER = 15;
    private static final BigDecimal MIN_SUIT_ROOM_COST = BigDecimal.valueOf(400);

    RoomOffer prepareRoom(String type,
                          String roomName, int roomSize, String additionalInfo, LocalDate date) {

        Room room;
        if ("Standard".equals(type)) {
            room = new StandardRoom(roomName, roomSize,
                    STANDARD_ROOM_COST_PER_METER, additionalInfo);

        } else if ("Suite".equals(type)) {
            room = new SuiteRoom(roomName, roomSize,
                    isPrivateBeachAvailableForSuiteRoom(date),
                    additionalInfo, MIN_SUIT_ROOM_COST);

        } else {
            throw new IllegalArgumentException("Unknown room type: " + type);
        }

        room.calculateRoomCost();
        return new RoomOffer(room.getId(), room.getPrice());
    }

    private boolean isPrivateBeachAvailableForSuiteRoom(LocalDate currentDate) {
        int currentMonth = currentDate.getMonthValue();

        if (currentMonth >= 6 && currentMonth <= 8) {
            log.info("Prywatna plaża dostępna.");
            return true;
        } else {
            log.info("Prywatna plaża niedostępna");
            return false;
        }
    }
}
