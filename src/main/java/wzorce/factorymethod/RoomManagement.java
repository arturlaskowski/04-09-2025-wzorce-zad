package wzorce.factorymethod;

import java.time.LocalDate;

abstract class RoomManagement {

    RoomOffer prepareRoom(String roomName, int roomSize, String additionalInfo, LocalDate date) {
        var room = createRoom(roomName, roomSize, additionalInfo, date);
        room.calculateRoomCost();
        return new RoomOffer(room.getId(), room.getPrice());
    }

    abstract Room createRoom(String roomName, int roomSize, String additionalInfo, LocalDate date);
}

