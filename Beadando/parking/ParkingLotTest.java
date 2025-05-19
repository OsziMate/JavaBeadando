package parking;

import static check.CheckThat.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import check.*;
import java.io.*;

import parking.facility.*;
import parking.*;
import vehicle.*;

public class ParkingLotTest{

    @Test
    public void testConstructorWithInvalidValues(){
        assertThrows(IllegalArgumentException.class, () -> {new ParkingLot(0, 0);});
        assertThrows(IllegalArgumentException.class, () -> {new ParkingLot(0, 2);});
        assertThrows(IllegalArgumentException.class, () -> {new ParkingLot(2, 0);});
    }

    @Test
    public void testTextualRepresentation(){
        ParkingLot parkingLot = new ParkingLot(4,4);
        Gate gate = new Gate(parkingLot);

        Car car1 = new Car("LGN-280", Size.SMALL, 2);
        gate.registerCar(car1);
        Car car2 = new Car("RKJ-520", Size.LARGE, 2);
        gate.registerCar(car2);
        Car car3 = new Car("KXC-984", Size.SMALL, 3);
        gate.registerCar(car3);

        gate.deRegisterCar("LGN-280");

        String elvart = "X X X X\nX X X X\nX L L X\nS X X X";
        assertEquals(elvart, parkingLot.toString());
    }
}