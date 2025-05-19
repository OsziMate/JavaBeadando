package parking.facility;

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

public class GateTest{

    private ParkingLot parkingLot;
    private Gate gate;

    @BeforeEach
    public void setUp() {
        parkingLot = new ParkingLot(4,4);
        gate = new Gate(parkingLot);
    }

    @Test 
    public void testFindAnyAvailableSpaceForCar(){
        Car car = new Car("KXC-984", Size.SMALL, 2);
        Space space = gate.findAnyAvailableSpaceForCar(car);

        assertNotNull(space, "Nem talált helyet, pedig kellett volna.");
        assertFalse(space.isTaken(), "A visszaadott hely foglalt.");
    }

    @ParameterizedTest
    @CsvSource({
        "LGN-280, SMALL, 2",
        "RKJ-520, LARGE, 2"
    })
    public void testFindPreferredAvailableSpaceForCar(String plate, Size size, int preferredFloor){
        Car car = new Car(plate, size, preferredFloor);
        Space space = gate.findPreferredAvailableSpaceForCar(car);

        assertNotNull(space, "Nem talált helyet, pedig kellett volna.");
        assertFalse(space.isTaken(), "A visszaadott hely foglalt.");        
    }

    @ParameterizedTest
    @CsvSource({
        "LGN-280, SMALL, 2",
        "RKJ-520, LARGE, 2"
    })
    public void testRegisterCar(String plate, Size size, int preferredFloor){
        Car car = new Car(plate, size, preferredFloor);

        assertTrue(gate.registerCar(car), "Sikeresen regisztráltuk az autót.");
    }

    @ParameterizedTest
    @CsvSource({
        "LGN-280, SMALL, 2",
        "RKJ-520, LARGE, 2"
    })
    public void testDeRegisterCar(String plate, Size size, int preferredFloor){
        Car car = new Car(plate, size, preferredFloor);

        gate.registerCar(car);
        gate.deRegisterCar(plate);
        assertEquals(0, gate.getCarsLength());
    }
}