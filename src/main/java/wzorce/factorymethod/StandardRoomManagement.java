package wzorce.factorymethod;

import java.time.LocalDate;

class StandardRoomManagement extends RoomManagement {

    private static final int COST_PER_METER = 15;

    @Override
    Room createRoom(String roomName, int roomSize, String additionalInfo, LocalDate localDate) {
        return new StandardRoom(
                roomName,
                roomSize,
                COST_PER_METER,
                additionalInfo
        );
    }
}