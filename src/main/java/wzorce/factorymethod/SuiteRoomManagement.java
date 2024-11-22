package wzorce.factorymethod;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
class SuiteRoomManagement extends RoomManagement {

    private static final BigDecimal MIN_SUIT_ROOM_COST = BigDecimal.valueOf(400);

    @Override
    Room createRoom(String roomName, int roomSize, String additionalInfo, LocalDate date) {
        return new SuiteRoom(
                roomName,
                roomSize,
                isPrivateBeachAvailable(date),
                additionalInfo,
                MIN_SUIT_ROOM_COST);
    }

    private boolean isPrivateBeachAvailable(LocalDate currentDate) {
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