package wzorce.factorymethod;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class RoomManagementTest {

    @Test
    void prepareStandardRoom() {
        //given
        var roomName = "Z widokiem na morze";
        var roomSize = 15;
        var additionalInfo = "Nie na parterze";
        var date = LocalDate.of(2024, 7, 15);

        //when
        RoomManagement standardRoomManagement = new StandardRoomManagement();
        RoomOffer roomOffer = standardRoomManagement.prepareRoom(roomName, roomSize, additionalInfo, date);

        //then
        assertThat(BigDecimal.valueOf(225)).isEqualTo(roomOffer.price());
    }

    @Test
    void prepareSuitRoom() {
        //given
        var roomName = "Premium Suite";
        var roomSize = 65;
        var additionalInfo = "Alergia na dywany";
        var date = LocalDate.of(2024, 8, 5);

        //when
        RoomManagement suiteRoomManagement = new SuiteRoomManagement();
        RoomOffer roomOffer = suiteRoomManagement.prepareRoom(roomName, roomSize, additionalInfo, date);

        //then
        assertThat(BigDecimal.valueOf(450)).isEqualTo(roomOffer.price());
    }
}